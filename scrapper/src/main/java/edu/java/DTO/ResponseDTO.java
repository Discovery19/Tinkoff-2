package edu.java.DTO;

import java.util.List;
import lombok.Getter;

@Getter
public class ResponseDTO {
    private Long id;
    private String description;
    private String link;
    private List<String> links;

    public ResponseDTO(Long id, String description, String link) {
        this.id = id;
        this.description = description;
        this.link = link;
    }

    public ResponseDTO(Long id, List<String> links) {
        this.id = id;
        this.links = links;
    }
}
