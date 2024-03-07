package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.bot_api.client.BotAPIClient;
import edu.java.bot_api.request.BotRequest;
import edu.java.bot_api.response.BotResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class ClientTest {
    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
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
        BotAPIClient client = new BotAPIClient("http://localhost:8080");
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
