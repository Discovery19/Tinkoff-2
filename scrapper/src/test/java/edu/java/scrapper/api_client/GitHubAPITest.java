package edu.java.scrapper.api_client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import edu.java.scrapper.api.response.client_response.RepositoryResponse;
import edu.java.scrapper.client.github.GitHubWebClient;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
public class GitHubAPITest {
     WireMockServer wireMockServer =  new WireMockServer(9090);
    @Test
    public void gitHubClientTest() throws IOException, URISyntaxException {
        wireMockServer.start();
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
        GitHubWebClient client = new GitHubWebClient("http://localhost:9090", new ApplicationConfig.RetryConfig(
            ApplicationConfig.BackOff.LINEAR, 3, 2));
        //when
        RepositoryResponse gitHubResponse = client.fetchRepository("testUser", "testRep").block();
        //then
        Assertions.assertEquals(
            new RepositoryResponse(
                OffsetDateTime.parse("2024-02-16T19:40:25Z")
                , "https://github.com/Discovery19/Tinkoff-2", 1
            ), gitHubResponse
        );
        wireMockServer.shutdown();
    }
}
