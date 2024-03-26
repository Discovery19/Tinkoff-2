package edu.scrapper.api.service;

import edu.scrapper.api.repositories.dto.LinkDTO;
import edu.scrapper.api.repositories.jdbc.JdbcLinksRepository;
import edu.scrapper.api.requests.LinkRequest;
import edu.scrapper.api.response.api_response.LinkResponse;
import edu.scrapper.api.response.api_response.ListLinksResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private final JdbcLinksRepository jdbcLinksRepository;

    public JdbcLinkService(JdbcLinksRepository jdbcLinksRepository) {
        this.jdbcLinksRepository = jdbcLinksRepository;
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) throws URISyntaxException {
        List<LinkResponse> linksList = new ArrayList<>();
        for (LinkDTO dto : jdbcLinksRepository.findAll(id)) {
            linksList.add(new LinkResponse(dto.id(), new URI(dto.url())));
        }
        return ResponseEntity.ok().body(new ListLinksResponse(linksList));
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) throws URISyntaxException {
        LinkDTO dto = jdbcLinksRepository.add(id, request.link());
        return ResponseEntity.ok().body(new LinkResponse(dto.id(), new URI(dto.url())));
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException {
        LinkDTO dto = jdbcLinksRepository.remove(id, request.link());
        return ResponseEntity.ok().body(new LinkResponse(dto.id(), new URI(dto.url())));
    }
}
