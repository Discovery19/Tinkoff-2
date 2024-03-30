package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import edu.java.scrapper.api.response.client_response.QuestionResponse;
import edu.java.scrapper.api.response.client_response.RepositoryResponse;
import edu.java.scrapper.client.github.GitHubClient;
import edu.java.scrapper.client.github.GitHubWebClient;
import edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import edu.java.scrapper.client.stackoverflow.StackOverflowWebClient;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
public class RetryTest {
    static WireMockServer wireMockServer;
    @BeforeAll
     static void configureWireMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }
    @AfterAll
    static void tearDownWireMock() {
    wireMockServer.shutdown();
    }
    @Test
    void shouldRetryOnErrorLinear() throws URISyntaxException, IOException {
        wireMockServer.start();
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step1")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step1")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step2")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step2")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step3")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step3")
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(RetryTest.class
                                    .getResource("/GitHubResponseExample.json")
                                ).toURI()
                            )
                        )
                    )
                )
        );

        ApplicationConfig.RetryConfig retryConfig =
            new ApplicationConfig.RetryConfig(ApplicationConfig.BackOff.LINEAR, 3, 2);
        //when
        GitHubClient githubClient =
            new GitHubWebClient("http://localhost:8080", retryConfig);
        RepositoryResponse response = githubClient.fetchRepository("testUser", "onError").block();
        //then
        Assertions.assertDoesNotThrow(() -> response);
        wireMockServer.shutdown();
    }
    @Test
    void shouldRetryOnErrorConstant() throws URISyntaxException, IOException {
        wireMockServer.start();
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step1")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step1")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step2")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step2")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step3")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/repos/testUser/onError"))
                .inScenario("retry")
                .whenScenarioStateIs("step3")
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(RetryTest.class
                                    .getResource("/GitHubResponseExample.json")
                                ).toURI()
                            )
                        )
                    )
                )
        );

        ApplicationConfig.RetryConfig retryConfig =
            new ApplicationConfig.RetryConfig(ApplicationConfig.BackOff.CONSTANT, 3, 2);
        //when
        GitHubClient githubClient =
            new GitHubWebClient("http://localhost:8080", retryConfig);
        RepositoryResponse response = githubClient.fetchRepository("testUser", "onError").block();
        //then
        Assertions.assertDoesNotThrow(() -> response);
        wireMockServer.shutdown();
    }
    @Test
    void shouldRetryOnErrorExponential() throws URISyntaxException, IOException {
        wireMockServer.start();
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .inScenario("retry")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step1")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .inScenario("retry")
                .whenScenarioStateIs("step1")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step2")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .inScenario("retry")
                .whenScenarioStateIs("step2")
                .willReturn(aResponse()
                    .withStatus(500)
                )
                .willSetStateTo("step3")
        );
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .inScenario("retry")
                .whenScenarioStateIs("step3")
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(RetryTest.class
                                    .getResource("/StackOverflowResponseExample.json")
                                ).toURI()
                            )
                        )
                    )
                )
        );

        ApplicationConfig.RetryConfig retryConfig =
            new ApplicationConfig.RetryConfig(ApplicationConfig.BackOff.EXPONENTIAL, 3, 1);
        //when
        StackOverflowClient stackOverflowClient =
            new StackOverflowWebClient("http://localhost:8080", retryConfig);
        QuestionResponse response = stackOverflowClient.fetchQuestion(20089818).block();
        //then
        Assertions.assertDoesNotThrow(() -> response);
        wireMockServer.shutdown();
    }
}
