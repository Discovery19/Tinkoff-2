package edu.java.scrapper.service;

import edu.java.scrapper.requests.LinkRequest;
import edu.java.scrapper.response.LinkResponse;
import edu.java.scrapper.response.ListLinksResponse;
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
