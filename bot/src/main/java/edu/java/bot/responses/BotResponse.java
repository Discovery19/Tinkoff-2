package edu.java.bot.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record BotResponse(@JsonProperty("link") URI link) {
}
