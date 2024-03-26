package edu.scrapper.bot_api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record BotResponse(@JsonProperty("link") URI link) {
}
