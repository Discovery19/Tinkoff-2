package edu.java.bot.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListLinkResponse(
    @JsonProperty("links")
    List<LinkResponse> links
) {
}
