package serviceTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.ScrapperAPIClient;
import edu.java.DTO.AllLinksDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class APITest {
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
        stubFor(
            post(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("1")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient();
        //when
        Long response = client.registerChat(1L).block();
        //then
        Assertions.assertEquals(
            1L, response

        );

    }

    @Test
    public void deleteChatTest() {
        //given
        wireMockServer.stubFor(
            delete(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("1")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient();
        //when
        Long response = client.deleteChat(1L).block();
        //then
        Assertions.assertEquals(
            1L, response

        );
    }

    @Test
    public void getLinksTest() throws URISyntaxException {
        //given
        wireMockServer.stubFor(
            get(urlEqualTo("/tg-chat/1/links"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"links\": [\n" +
                        "        \"link\"\n" +
                        "    ]\n" +
                        "}")
                )
        );
        ScrapperAPIClient client = new ScrapperAPIClient();
        //when
        AllLinksDTO response = client.getLinks(1L).block();
        //then
        Assertions.assertEquals(
            new AllLinksDTO(1L, new ArrayList<>(List.of(new URI("link")))), response

        );
    }
}
