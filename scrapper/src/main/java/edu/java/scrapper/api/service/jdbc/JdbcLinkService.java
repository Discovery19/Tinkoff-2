package edu.java.scrapper.api.service.jdbc;

import edu.java.scrapper.api.repositories.dto.LinkDTO;
import edu.java.scrapper.api.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import edu.java.scrapper.api.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) throws URISyntaxException {
        List<LinkResponse> linksList = new ArrayList<>();
        for (LinkDTO dto : jdbcLinkRepository.findAll(id)) {
            linksList.add(new LinkResponse(dto.id(), new URI(dto.url())));
        }
        return ResponseEntity.ok().body(new ListLinksResponse(linksList));
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) throws URISyntaxException {
        LinkDTO dto = jdbcLinkRepository.add(id, request.link());
        return ResponseEntity.ok().body(new LinkResponse(dto.id(), new URI(dto.url())));
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException {
        LinkDTO dto = jdbcLinkRepository.remove(id, request.link());
        return ResponseEntity.ok().body(new LinkResponse(dto.id(), new URI(dto.url())));
    }
}
