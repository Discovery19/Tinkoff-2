package edu.java.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    @JsonProperty("pushed_at") OffsetDateTime pushedAt,
    @JsonProperty("html_url") String link
) {
}
