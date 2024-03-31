package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.bot_api.client.BotAPIClient;
import edu.java.scrapper.bot_api.request.BotRequest;
import edu.java.scrapper.bot_api.response.BotResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class ClientTest {
    static WireMockServer wireMockServer;

    @BeforeAll
    static void configureWireMock() {
        wireMockServer = new WireMockServer(6060);
        wireMockServer.start();
    }

    @AfterAll
    static void tearDownWireMock() {
        wireMockServer.shutdown();
    }

    @Test
    public void updateTest() throws URISyntaxException {
        //given
        wireMockServer.stubFor(
            post(urlEqualTo("/updates"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"link\": \"link.com\",\n" +
                        "    \"description\": \"link\",\n" +
                        "    \"tgChatIds\": [\n" +
                        "        \"link.com\"\n" +
                        "    ]\n" +
                        "}")
                )
        );
        BotAPIClient client = new BotAPIClient("http://localhost:6060", new ApplicationConfig.RetryConfig(
            ApplicationConfig.BackOff.CONSTANT, 1, 1));
        //when
        BotResponse response =
            client.sendUpdate(new BotRequest(1L, new URI("link.com"), "link", List.of(1L))).getBody();
        URI result = response.link();
        //then
        Assertions.assertEquals(
            URI.create("link.com"), result
        );
    }
}
