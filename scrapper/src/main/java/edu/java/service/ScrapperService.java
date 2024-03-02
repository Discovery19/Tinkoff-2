package edu.java.service;

import edu.java.response.LinkResponse;
import edu.java.response.ListLinksResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScrapperService {
    public ResponseEntity<Long> defaultAnswer(Long id) {
        return ResponseEntity.ok(id);
    }

    public ResponseEntity<LinkResponse> uriAnswer() {
        //bd register ok/bad
        try {
            return ResponseEntity.ok(new LinkResponse(new URI("default")));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    public ResponseEntity<ListLinksResponse> linksResponse(Long id) {
        //get links from bd by id
        return ResponseEntity.ok(new ListLinksResponse(List.of()));
    }

}
