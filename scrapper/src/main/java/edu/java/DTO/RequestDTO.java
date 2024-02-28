package edu.java.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record RequestDTO(@JsonProperty("id") Long id, @JsonProperty("link") URI link) {

}
