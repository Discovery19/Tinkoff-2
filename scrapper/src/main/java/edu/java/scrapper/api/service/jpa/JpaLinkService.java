package edu.java.scrapper.api.service.jpa;

import edu.java.scrapper.api.exceptions.ResourceNotFoundException;
import edu.java.scrapper.api.model.Link;
import edu.java.scrapper.api.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.api.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import edu.java.scrapper.api.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long chatId) {
        var chat = chatRepository.findById(chatId).orElseThrow(ResourceNotFoundException::new);
        List<LinkResponse> responses =
            chat.getLinkChat().stream().map(link -> {
                try {
                    return new LinkResponse(link.getId(), new URI(link.getUrl()));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

        return ResponseEntity.ok(new ListLinksResponse(responses));
    }

    @Override
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) throws URISyntaxException {
        //check chat exists
        var chat = chatRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        //
        var link = linkRepository.findByUrl(request.link().toString()).orElseGet(
            () -> {
                var url = new Link();
                url.setUrl(request.link().toString());
                linkRepository.save(url);
                return url;
            }
        );
        chat.getLinkChat().add(link);
        chatRepository.saveAndFlush(chat);
        return ResponseEntity.ok(new LinkResponse(link.getId(), new URI(link.getUrl())));
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException {
        var chat = chatRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        var link = linkRepository.findByUrl(request.link().toString()).orElseThrow(ResourceNotFoundException::new);
        linkRepository.delete(link);
        chat.getLinkChat().remove(link);
        chatRepository.saveAndFlush(chat);
        return ResponseEntity.ok(new LinkResponse(link.getId(), new URI(link.getUrl())));
    }
}
