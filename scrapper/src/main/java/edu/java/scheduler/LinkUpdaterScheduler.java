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
    private final SchedulerRepository linksRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        List<URI> linksList = linksRepository.findLinksBefore(OffsetDateTime.now()
            .minus(applicationConfig.scheduler().linkCheckingFrequency()));

        for (URI url : linksList) {
            var newUpdate = fetchUrl(url);
            var lastUpdate = linksRepository.getLastUpdate(url);
            linksRepository.updateCheckedAt(url, OffsetDateTime.now());
            if (newUpdate.isAfter(lastUpdate)) {
                var id = linksRepository.getIdByLink(url);
                botAPIClient
                    .sendUpdate(
                        new BotRequest(id, url,
                            "Новое обновление",
                            linksRepository.getSubscribedChats(id)
                        ));
                linksRepository.updateUpdatedAt(url, newUpdate);
            }
        }
    }

    private OffsetDateTime fetchUrl(URI url) {
        String pattern = "https://github.com/(\\w+)/([^/]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(String.valueOf(url));
        if (m.find()) {
            String owner = m.group(1);
            String repo = m.group(2);
            RepositoryResponse response = gitHubClient.fetchRepository(owner, repo).block();
            return response.pushedAt();
        } else {
            long questionId = Long.parseLong(Objects.requireNonNull(Arrays.stream(url.toString().split("/"))
                .filter(part -> part.matches("\\d+"))
                .findFirst().orElse(null)));
            QuestionResponse response = stackOverflowClient.fetchQuestion(questionId).block();
            return response.items().getFirst().lastActivityDate();
        }
    }

}
