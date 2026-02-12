package br.com.amisahdev.meetapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "event_organization",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"event_id", "organizer_id"})
        })
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EventOrganizationEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private UserEntity organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
}
