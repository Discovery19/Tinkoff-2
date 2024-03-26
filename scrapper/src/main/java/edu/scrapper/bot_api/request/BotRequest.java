package edu.scrapper.bot_api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record BotRequest(
    @JsonProperty("id") Long id,
    @JsonProperty("link") URI link,
    @JsonProperty("description") String description,
    @JsonProperty("tgChatIds") List<Long> tgChatsIds) {
}
