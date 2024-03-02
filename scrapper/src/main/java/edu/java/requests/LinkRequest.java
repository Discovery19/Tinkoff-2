package edu.java.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record LinkRequest(@JsonProperty("link") URI link) {
}
