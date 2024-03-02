package serviceTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.controllers.ScrapperController;
import edu.java.requests.LinkRequest;
import edu.java.response.LinkResponse;
import edu.java.response.ListLinksResponse;
import edu.java.service.ScrapperService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
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
        ScrapperController client = new ScrapperController(new ScrapperService());
        //when
        ResponseEntity<Long> response = client.registerChat(1L);
        //then
        Assertions.assertEquals(
            1L, response.getBody()

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
        ScrapperController client = new ScrapperController(new ScrapperService());
        //when
        ResponseEntity<Long> response = client.deleteChat(1L);
        //then
        Assertions.assertEquals(
            1L, response.getBody()
        );
    }

    //тесты с линками будут, когда будет нормальный сервис, а сервис, когда будет бд)))))

//    @Test
//    public void getLinksTest() {
//        //given
//        wireMockServer.stubFor(
//            get(urlEqualTo("/tg-chat/1/links"))
//                .willReturn(aResponse()
//                    .withHeader("Content-Type", "application/json")
//                    .withBody("{\n" +
//                        "    \"links\": [\n" +
//                        "        \"link.com\"\n" +
//                        "    ]\n" +
//                        "}")
//                )
//        );
//        ScrapperController client = new ScrapperController(new ScrapperService());
//        //when
//        ResponseEntity<ListLinksResponse> response = client.getLinks(1L);
//        System.out.println(response.getBody());
//        List<URI> result = response.getBody().links();
//        System.out.println(result.getFirst());
//        //then
//        Assertions.assertEquals(
//            "link.com", result.getFirst().toString()
//        );
//    }
//@Test
//public void trackLinkTest() throws URISyntaxException {
//    //given
//    wireMockServer.stubFor(
//        post(urlEqualTo("/tg-chat/1/links"))
//            .willReturn(aResponse()
//                .withHeader("Content-Type", "application/json")
//                    .withBody("{\n" +
//                        "\"link.com\"\n" +
//                        "}")
//            )
//    );
//    ScrapperController client = new ScrapperController(new ScrapperService());
//    //when
//    ResponseEntity<LinkResponse> response = client.trackLink(1L, new LinkRequest(new URI("link.com")));
//    //then
//    Assertions.assertEquals(
//        "link.com", response.getBody().link().toString()
//    );
//}
}
