package edu.java.scrapper.api_client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import edu.java.scrapper.api.response.client_response.QuestionResponse;
import edu.java.scrapper.client.stackoverflow.StackOverflowWebClient;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

//@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
public class StackOverflowAPITest {
     WireMockServer wireMockServer = new WireMockServer(7070);

//    @BeforeAll
//    static void configureWireMock() {
//        wireMockServer = new WireMockServer();
//        wireMockServer.start();
//    }
//
//    @AfterAll
//    static void tearDownWireMock() {
//        wireMockServer.shutdown();
//    }

    @Test
    public void testStackOverflowAPI() throws IOException, URISyntaxException {
        wireMockServer.start();
        //given
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(StackOverflowAPITest.class
                                    .getResource("/StackOverflowResponseExample.json")
                                ).toURI()
                            )
                        )
                    )
                )
        );
        StackOverflowWebClient client =
            new StackOverflowWebClient("http://localhost:7070", new ApplicationConfig.RetryConfig(
                ApplicationConfig.BackOff.LINEAR, 3, 2));
        //when
        QuestionResponse response = client.fetchQuestion(20089818).block();
        //then
        Assertions.assertEquals(
            new QuestionResponse(
                List.of(new QuestionResponse.QuestionItem(
                    OffsetDateTime.parse("2014-05-30T05:29:52Z")
                    , "Get questions content from Stack Exchange API"
                    , "https://stackoverflow.com/questions/20089818/get-questions-content-from-stack-exchange-api", 2))
            ), response
        );
        wireMockServer.shutdown();
    }
}
