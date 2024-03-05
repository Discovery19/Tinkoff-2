package edu.java.bot.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record LinkResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("link")
    URI link
) {

}
