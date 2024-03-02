package edu.java.controllers;

import edu.java.requests.LinkRequest;
import edu.java.response.LinkResponse;
import edu.java.response.ListLinksResponse;
import edu.java.service.ScrapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapperController implements ScrapperInterface {
    ScrapperService scrapperService;

    public ScrapperController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return scrapperService.defaultAnswer(id);
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return scrapperService.defaultAnswer(id);
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        return scrapperService.linksResponse(id);
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest dto) {
        return scrapperService.uriAnswer();
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest dto) {
        return scrapperService.uriAnswer();
    }


}
