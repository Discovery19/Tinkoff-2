package edu.java.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @RequiredArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "id")
    private Long id;
    @ManyToMany
    @JoinTable(
        name = "link_chat",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    ) @ToString.Exclude List<Link> linkChat;
}
