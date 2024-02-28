package edu.java.DTO;
//CHECKSTYLE:OFF: checkstyle:ImportOrder
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
public class AllLinksDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("links")
    private List<URI> links;

    public AllLinksDTO(Long id, List<URI> links) {
        this.id = id;
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllLinksDTO that = (AllLinksDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, links);
    }
}
