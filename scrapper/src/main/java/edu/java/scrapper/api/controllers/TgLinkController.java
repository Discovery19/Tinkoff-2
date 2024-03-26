package edu.scrapper.api.controllers;

import edu.scrapper.api.requests.LinkRequest;
import edu.scrapper.api.response.api_response.LinkResponse;
import edu.scrapper.api.response.api_response.ListLinksResponse;
import edu.scrapper.api.service.LinkService;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TgLinkController implements LinkController {
    private final LinkService linkService;

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
