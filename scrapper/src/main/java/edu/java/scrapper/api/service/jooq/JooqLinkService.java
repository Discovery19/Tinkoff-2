package edu.java.scrapper.api.service.jooq;

import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import edu.java.scrapper.api.service.LinkService;
import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.jooq.tables.LinkChat;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public class JooqLinkService implements LinkService {
    private final DSLContext dslContext;
    private final Chats chat = Chats.CHATS;
    private final Links link = Links.LINKS;
    private final LinkChat linkChat = LinkChat.LINK_CHAT;

    public JooqLinkService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        List<LinkResponse> responseList = dslContext.select(link.ID, link.URL)
            .from(link)
            .join(linkChat).on(link.ID.eq(linkChat.LINK_ID))
            .where(linkChat.CHAT_ID.eq(id))
            .fetchInto(LinkResponse.class);

        return ResponseEntity.ok(new ListLinksResponse(responseList));
    }

    @Override
    @Transactional
    public ResponseEntity<LinkResponse> trackLink(Long id, LinkRequest request) {
        var trackLink = request.link();
        ChatsRecord chatRecord = dslContext.selectFrom(chat)
            .where(chat.ID.eq(id))
            .fetchOne();

        LinksRecord linkRecord =

            dslContext.insertInto(this.link)
                .set(this.link.URL, trackLink.toString())
                .returning(this.link.ID, this.link.URL)
                .fetchOne();
        dslContext.insertInto(linkChat)
            .set(linkChat.LINK_ID, linkRecord.getId())
            .set(linkChat.CHAT_ID, chatRecord.getId())
            .execute();

        return ResponseEntity.ok(new LinkResponse(linkRecord.getId(), URI.create(linkRecord.get(this.link.URL))));
    }

    @Override
    public ResponseEntity<LinkResponse> untrackLink(Long id, LinkRequest request) throws URISyntaxException {
        var trackLink = request.link();
        Record2<Long, String> result = dslContext.select(this.link.ID, this.link.URL)
            .from(this.link)
            .where(this.link.URL.eq(trackLink.toString()))
            .fetchOne();

        Long recordId = result.value1();
        String recordUrl = result.value2();

        dslContext.deleteFrom(linkChat)
            .where(linkChat.CHAT_ID.eq(id))
            .and(linkChat.LINK_ID.eq(recordId))
            .execute();

        int remainingAssignments = Objects.requireNonNull(dslContext.selectCount()
            .from(linkChat)
            .where(linkChat.LINK_ID.eq(recordId))
            .fetchOne(0, int.class));
        if (remainingAssignments == 0) {
            dslContext.deleteFrom(this.link)
                .where(this.link.ID.eq(recordId))
                .execute();
        }
        return ResponseEntity.ok(new LinkResponse(recordId, URI.create(recordUrl)));
    }
}
