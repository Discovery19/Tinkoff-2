package edu.java.scheduler;

import edu.java.api.repositories.SchedulerRepository;
import edu.java.api.response.client_response.QuestionResponse;
import edu.java.api.response.client_response.RepositoryResponse;
import edu.java.bot_api.client.BotAPIClient;
import edu.java.bot_api.request.BotRequest;
import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.configuration.ApplicationConfig;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final ApplicationConfig applicationConfig;
    private final BotAPIClient botAPIClient;
    private final SchedulerRepository repository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        List<URI> linksList = repository.findLinksBefore(OffsetDateTime.now()
            .minus(applicationConfig.scheduler().linkCheckingFrequency()));

        for (URI url : linksList) {
            var newUpdate = fetchUrl(url);
            var lastUpdate = repository.getLastUpdate(url);
            repository.updateCheckedAt(url, OffsetDateTime.now());

            if (newUpdate.isAfter(lastUpdate)) {
                var id = repository.getIdByLink(url);

                botAPIClient
                    .sendUpdate(
                        new BotRequest(id, url,
                            whatHappens(url),
                            repository.getSubscribedChats(id)
                        ));
                repository.updateUpdatedAt(url, newUpdate);
            }
        }
    }

    private String whatHappens(URI url) {
        var gitCount = repository.checkGitColumn(url);
        var stackCount = repository.checkStackColumn(url);
        if (gitCount == null) {
            long questionId = parseStackUrl(url.toString());
            int answerCount = stackOverflowClient.fetchQuestion(questionId).block().items().getFirst().answerCount();
            if (stackCount < answerCount) {
                repository.insertStackColumn(url, answerCount);
                return "Новый ответ на StackOverFlow";
            } else {
                return "Статус StackOverflow ссылки обновился)))";
            }
        } else {
            String[] git = parseGitHubUrl(url.toString());
            String owner = git[0];
            String repo = git[1];
            int openIssues = gitHubClient.fetchRepository(owner, repo).block().openIssues();
            if (gitCount < openIssues) {
                repository.insertGitColumn(url, openIssues);
                return "Новая проблема на GitHub";
            } else {
                return "Статус вашего репозитория обновился))";
            }
        }
    }

    private OffsetDateTime fetchUrl(URI url) {
        String[] git = parseGitHubUrl(url.toString());
        if (git.length > 0) {
            String owner = git[0];
            String repo = git[1];
            RepositoryResponse response = gitHubClient.fetchRepository(owner, repo).block();
            insertColumnGit(url, owner, repo);
            //костыль для open issues
            if (response.openIssues() > repository.checkGitColumn(url)) {
                return OffsetDateTime.now();
            }

            return response.pushedAt();
        } else {
            long questionId = parseStackUrl(url.toString());
            QuestionResponse response = stackOverflowClient.fetchQuestion(questionId).block();
            insertColumnStack(url, questionId);
            return response.items().getFirst().lastActivityDate();
        }
    }

    private String[] parseGitHubUrl(String url) {
        String pattern = "https://github.com/(\\w+)/([^/]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(String.valueOf(url));
        if (m.find()) {
            String owner = m.group(1);
            String repo = m.group(2);
            return new String[] {owner, repo};
        }
        return new String[] {};
    }

    private Long parseStackUrl(String url) {
        return Long.parseLong(Objects.requireNonNull(Arrays.stream(url.split("/"))
            .filter(part -> part.matches("\\d+"))
            .findFirst().orElse(null)));
    }

    private void insertColumnGit(URI url, String owner, String repo) {
        if (repository.checkGitColumn(url) == null) {
            int openIssues = Objects.requireNonNull(gitHubClient.fetchRepository(owner, repo).block()).openIssues();
            repository.insertGitColumn(url, openIssues);
        }

    }

    private void insertColumnStack(URI url, long questionId) {
        if (repository.checkStackColumn(url) == null) {
            int answerCount =
                Objects.requireNonNull(stackOverflowClient.fetchQuestion(questionId).block()).items().getFirst()
                    .answerCount();
            repository.insertStackColumn(url, answerCount);
        }
    }
}
