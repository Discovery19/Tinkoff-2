package edu.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record ListLinksResponse(@JsonProperty("links")List<URI> links) {
}
