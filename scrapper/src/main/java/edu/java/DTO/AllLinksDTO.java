package edu.java.DTO;

import lombok.Getter;
import java.net.URI;
import java.util.List;
@Getter
public class AllLinksDTO {
    private Long id;
    private List<URI> links;
    public AllLinksDTO(Long id, List<URI> links) {
        this.id = id;
        this.links = links;
    }
}
