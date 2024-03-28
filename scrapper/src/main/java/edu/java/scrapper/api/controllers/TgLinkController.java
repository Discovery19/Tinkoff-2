package edu.java.scrapper.api.controllers;

import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import edu.java.scrapper.api.service.LinkService;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgLinkController implements LinkController {
    private final LinkService linkService;

    public TgLinkController(@Qualifier("jooqLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) throws URISyntaxException {
        return linkService.getLinks(id);
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) throws URISyntaxException {
        return linkService.trackLink(id, request);
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException {
        return linkService.untrackLink(id, request);
    }
}
