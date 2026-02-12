package br.com.amisahdev.meetapi.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EventEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "event_topic",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @Builder.Default
    private List<TopicEntity> topics = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EventOrganizationEntity> organizations = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EventSubscriptionEntity> subscriptions = new ArrayList<>();

}
