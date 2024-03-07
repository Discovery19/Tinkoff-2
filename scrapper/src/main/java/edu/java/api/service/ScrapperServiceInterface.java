package edu.java.api.service;

import edu.java.api.response.api_response.LinkResponse;
import edu.java.api.response.api_response.ListLinksResponse;
import edu.java.api.requests.LinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ScrapperServiceInterface {
    //here will be exceptions

    ResponseEntity<Long> registerChat(Long id);

    ResponseEntity<Long> deleteChat(Long id);

    ResponseEntity<ListLinksResponse> getLinks(Long id);

    ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request);

    ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request);

}
