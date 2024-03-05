package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.stackoverflow.StackOverflowWebClient;
import edu.java.response.QuestionResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class StackOverflowAPITest {
    @Test
    public void testStackOverflowAPI() throws IOException, URISyntaxException {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
        //given
        wireMockServer.stubFor(
            get(urlEqualTo("/2.3/questions/20089818?order=desc&sort=activity&site=stackoverflow"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readAllBytes(
                            Path.of(Objects.requireNonNull(StackOverflowAPITest.class
                                    .getResource("/StackOverflowResponseExample")
                                ).toURI()
                            )
                        )
                    )
                )
        );
        StackOverflowWebClient client = new StackOverflowWebClient("http://localhost:8080");
        //when
        QuestionResponse response = client.fetchQuestion(20089818).block();
        //then
        Assertions.assertEquals(
            new QuestionResponse(
                List.of(new QuestionResponse.QuestionItem(
                    OffsetDateTime.parse("2014-05-30T05:29:52Z")
                    , "Get questions content from Stack Exchange API"
                    , "https://stackoverflow.com/questions/20089818/get-questions-content-from-stack-exchange-api"))
            ), response
        );
        wireMockServer.shutdown();
    }
}
