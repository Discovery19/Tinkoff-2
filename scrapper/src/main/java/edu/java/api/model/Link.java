package edu.java.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.Data;

@Entity
@Data
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(generator = "links_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "links_id_generator", sequenceName = "links_id_seq", allocationSize = 1)
    Long id;
    @Column(name = "url", unique = true)
    String url;
    @Column(name = "description")
    String description;
    @Column(name = "updated_at")
    OffsetDateTime updatedAt;
    @Column(name = "last_checked")
    OffsetDateTime lastChecked;
    @Column(name = "open_issues")
    Integer openIssues;
    @Column(name = "answer_count")
    Integer answerCount;

    @ManyToMany(mappedBy = "linkChat")
    Collection<Chat> links;
}
