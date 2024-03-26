package edu.java.scrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record LinkResponse(@JsonProperty("link") URI link) {
}
