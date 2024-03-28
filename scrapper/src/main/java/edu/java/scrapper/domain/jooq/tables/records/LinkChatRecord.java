/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.records;

import edu.java.scrapper.domain.jooq.tables.LinkChat;
import java.beans.ConstructorProperties;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class LinkChatRecord extends UpdatableRecordImpl<LinkChatRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK_CHAT.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK_CHAT.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK_CHAT.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK_CHAT.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return LinkChat.LINK_CHAT.LINK_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return LinkChat.LINK_CHAT.CHAT_ID;
    }

    @Override
    @NotNull
    public Long component1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getChatId();
    }

    @Override
    @NotNull
    public Long value1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getChatId();
    }

    @Override
    @NotNull
    public LinkChatRecord value1(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkChatRecord value2(@NotNull Long value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkChatRecord values(@NotNull Long value1, @NotNull Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkChatRecord
     */
    public LinkChatRecord() {
        super(LinkChat.LINK_CHAT);
    }

    /**
     * Create a detached, initialised LinkChatRecord
     */
    @ConstructorProperties({"linkId", "chatId"})
    public LinkChatRecord(@NotNull Long linkId, @NotNull Long chatId) {
        super(LinkChat.LINK_CHAT);

        setLinkId(linkId);
        setChatId(chatId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkChatRecord
     */
    public LinkChatRecord(edu.java.scrapper.domain.jooq.tables.pojos.LinkChat value) {
        super(LinkChat.LINK_CHAT);

        if (value != null) {
            setLinkId(value.getLinkId());
            setChatId(value.getChatId());
            resetChangedOnNotNull();
        }
    }
}