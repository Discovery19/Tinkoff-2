package edu.java.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.bot.client.ScrapperAPIClient;
import edu.java.bot.responses.LinkResponse;
import edu.java.bot.responses.ListLinkResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
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
    public void registerTest() {
        //given
        wireMockServer.stubFor(
            post(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("1")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient("http://localhost:8080");
        //when
        Long response = client.registerChat(1L).getBody();
        //then
        Assertions.assertEquals(
            1L, response
        );
    }
    @Test
    public void deleteTest() {
        //given
        wireMockServer.stubFor(
            delete(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("1")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient("http://localhost:8080");
        //when
        Long response = client.deleteChat(1L).getBody();
        //then
        Assertions.assertEquals(
            1L, response
        );
    }
    @Test
    public void getLinksTest() {
        //given
        wireMockServer.stubFor(
            get(urlEqualTo("/tg-chat/1/links"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "    \"links\": [\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"link\": \"link.com\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient("http://localhost:8080");
        //when
        ResponseEntity<ListLinkResponse> response = client.getLinks(1L);
        URI result = response.getBody().links().getFirst().link();

        //then
        Assertions.assertEquals(
            URI.create("link.com"), result
        );
    }
    @Test
    public void trackLinkTest() throws URISyntaxException {
        //given
        wireMockServer.stubFor(
            post(urlEqualTo("/tg-chat/1/links"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "            \"id\": 1,\n" +
                        "            \"link\": \"link.com\"\n" +
                        "}")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient("http://localhost:8080");
        //when
        ResponseEntity<LinkResponse> response = client.trackLink(1L, new URI("link.com"));
        URI result = response.getBody().link();

        //then
        Assertions.assertEquals(
            URI.create("link.com"), result
        );
    }
    @Test
    public void deleteLinkTest() throws URISyntaxException {
        //given
        wireMockServer.stubFor(
            delete(urlEqualTo("/tg-chat/1/links"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "            \"id\": 1,\n" +
                        "            \"link\": \"link.com\"\n" +
                        "}")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient("http://localhost:8080");
        //when
        ResponseEntity<LinkResponse> response = client.deleteLink(1L, new URI("link.com"));
        URI result = response.getBody().link();
        //then
        Assertions.assertEquals(
            URI.create("link.com"), result
        );
    }
}
