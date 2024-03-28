/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.records;

import edu.java.scrapper.domain.jooq.tables.Links;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class LinksRecord extends UpdatableRecordImpl<LinksRecord>
    implements Record7<Long, String, String, OffsetDateTime, OffsetDateTime, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINKS.DESCRIPTION</code>.
     */
    public void setDescription(@Nullable String value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINKS.DESCRIPTION</code>.
     */
    @Size(max = 1000000000)
    @Nullable
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>LINKS.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@Nullable OffsetDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINKS.UPDATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(3);
    }

    /**
     * Setter for <code>LINKS.LAST_CHECKED</code>.
     */
    public void setLastChecked(@Nullable OffsetDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>LINKS.LAST_CHECKED</code>.
     */
    @Nullable
    public OffsetDateTime getLastChecked() {
        return (OffsetDateTime) get(4);
    }

    /**
     * Setter for <code>LINKS.OPEN_ISSUES</code>.
     */
    public void setOpenIssues(@Nullable Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>LINKS.OPEN_ISSUES</code>.
     */
    @Nullable
    public Integer getOpenIssues() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>LINKS.ANSWER_COUNT</code>.
     */
    public void setAnswerCount(@Nullable Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>LINKS.ANSWER_COUNT</code>.
     */
    @Nullable
    public Integer getAnswerCount() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row7<Long, String, String, OffsetDateTime, OffsetDateTime, Integer, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row7<Long, String, String, OffsetDateTime, OffsetDateTime, Integer, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Links.LINKS.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Links.LINKS.URL;
    }

    @Override
    @NotNull
    public Field<String> field3() {
        return Links.LINKS.DESCRIPTION;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field4() {
        return Links.LINKS.UPDATED_AT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field5() {
        return Links.LINKS.LAST_CHECKED;
    }

    @Override
    @NotNull
    public Field<Integer> field6() {
        return Links.LINKS.OPEN_ISSUES;
    }

    @Override
    @NotNull
    public Field<Integer> field7() {
        return Links.LINKS.ANSWER_COUNT;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @Nullable
    public String component3() {
        return getDescription();
    }

    @Override
    @Nullable
    public OffsetDateTime component4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime component5() {
        return getLastChecked();
    }

    @Override
    @Nullable
    public Integer component6() {
        return getOpenIssues();
    }

    @Override
    @Nullable
    public Integer component7() {
        return getAnswerCount();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @Nullable
    public String value3() {
        return getDescription();
    }

    @Override
    @Nullable
    public OffsetDateTime value4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime value5() {
        return getLastChecked();
    }

    @Override
    @Nullable
    public Integer value6() {
        return getOpenIssues();
    }

    @Override
    @Nullable
    public Integer value7() {
        return getAnswerCount();
    }

    @Override
    @NotNull
    public LinksRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value3(@Nullable String value) {
        setDescription(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value4(@Nullable OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value5(@Nullable OffsetDateTime value) {
        setLastChecked(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value6(@Nullable Integer value) {
        setOpenIssues(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value7(@Nullable Integer value) {
        setAnswerCount(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord values(
        @Nullable Long value1,
        @NotNull String value2,
        @Nullable String value3,
        @Nullable OffsetDateTime value4,
        @Nullable OffsetDateTime value5,
        @Nullable Integer value6,
        @Nullable Integer value7
    ) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinksRecord
     */
    public LinksRecord() {
        super(Links.LINKS);
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    @ConstructorProperties({"id", "url", "description", "updatedAt", "lastChecked", "openIssues", "answerCount"})
    public LinksRecord(
        @Nullable Long id,
        @NotNull String url,
        @Nullable String description,
        @Nullable OffsetDateTime updatedAt,
        @Nullable OffsetDateTime lastChecked,
        @Nullable Integer openIssues,
        @Nullable Integer answerCount
    ) {
        super(Links.LINKS);

        setId(id);
        setUrl(url);
        setDescription(description);
        setUpdatedAt(updatedAt);
        setLastChecked(lastChecked);
        setOpenIssues(openIssues);
        setAnswerCount(answerCount);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    public LinksRecord(edu.java.scrapper.domain.jooq.tables.pojos.Links value) {
        super(Links.LINKS);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setDescription(value.getDescription());
            setUpdatedAt(value.getUpdatedAt());
            setLastChecked(value.getLastChecked());
            setOpenIssues(value.getOpenIssues());
            setAnswerCount(value.getAnswerCount());
            resetChangedOnNotNull();
        }
    }
}