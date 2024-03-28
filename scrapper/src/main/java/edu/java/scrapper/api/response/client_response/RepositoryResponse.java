package edu.java.scrapper.api.response.client_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    @JsonProperty("pushed_at") OffsetDateTime pushedAt,
    @JsonProperty("html_url") String link,
    @JsonProperty("open_issues") int openIssues
) {
}
