package edu.java.scrapper.api.service;

import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;

public interface LinkService {

    ResponseEntity<ListLinksResponse> getLinks(Long id) throws URISyntaxException;

    ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) throws URISyntaxException;

    ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException;

}
