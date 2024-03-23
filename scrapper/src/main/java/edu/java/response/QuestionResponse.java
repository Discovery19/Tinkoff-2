package edu.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(
    @JsonProperty("items")
    List<QuestionItem> items
) {
    public record QuestionItem(
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("title") String title,
        @JsonProperty("link") String link
    ) {

    }
}

