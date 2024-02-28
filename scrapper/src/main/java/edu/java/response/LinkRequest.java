package edu.java.response;

import java.net.URI;
import lombok.Getter;

@Getter
public class LinkRequest {
    private long id;
    private URI url;
    private String description;

    public LinkRequest(long id, URI url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }
}
