package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.gitHub.GitHubWebClient;
import edu.java.response.RepositoryResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class GitHubAPITest {
    @Test
    public void gitHubClientTest() throws IOException, URISyntaxException {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
        //given
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/testRep"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(GitHubAPITest.class
                                    .getResource("/GitHubResponseExample.json")
                                ).toURI()
                            )
                        )
                    )
                )
        );
        GitHubWebClient client = new GitHubWebClient("http://localhost:8080");
        //when
        RepositoryResponse gitHubResponse = client.fetchRepository("testUser", "testRep").block();
        //then
        Assertions.assertEquals(
            new RepositoryResponse(
                OffsetDateTime.parse("2024-02-16T19:40:25Z")
                , "https://github.com/Discovery19/Tinkoff-2"
            ), gitHubResponse

        );
        wireMockServer.shutdown();
    }
}
