package edu.java.scrapper.controllers;

import edu.java.scrapper.requests.LinkRequest;
import edu.java.scrapper.response.LinkResponse;
import edu.java.scrapper.response.ListLinksResponse;
import edu.java.scrapper.service.ScrapperServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapperController implements ScrapperInterface {
    private final ScrapperServiceInterface scrapperService;

    public ScrapperController(ScrapperServiceInterface scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return scrapperService.registerChat(id);
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return scrapperService.deleteChat(id);
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        return scrapperService.getLinks(id);
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) {
        return scrapperService.trackLink(id, request);
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) {
        return scrapperService.untrackLink(id, request);
    }

}
