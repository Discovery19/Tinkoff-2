package edu.java.api.service.jooq;

import edu.java.api.service.TgChatService;
import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.jooq.tables.LinkChat;
import edu.java.scrapper.domain.jooq.tables.Links;
import org.jooq.DSLContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.impl.DSL.selectOne;

public class JooqChatService implements TgChatService {

    private final DSLContext dslContext;
    private final Chats chat = Chats.CHATS;
    private final Links link = Links.LINKS;

    private final LinkChat linkChat = LinkChat.LINK_CHAT;

    public JooqChatService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        dslContext.insertInto(chat)
            .set(chat.ID, id)
            .execute();
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<Long> deleteChat(Long id) {
        dslContext.deleteFrom(link)
            .whereExists(
                selectOne()
                    .from(linkChat)
                    .where(linkChat.LINK_ID.eq(link.ID))
                    .andNotExists(
                        selectOne()
                            .from(linkChat)
                            .where(linkChat.LINK_ID.eq(link.ID))
                            .and(linkChat.CHAT_ID.ne(id))
                    )
            )
            .execute();
        Long deleted = (long) dslContext.deleteFrom(chat)
            .where(chat.ID.eq(id))
            .execute();
        return ResponseEntity.ok(deleted);
    }
}
