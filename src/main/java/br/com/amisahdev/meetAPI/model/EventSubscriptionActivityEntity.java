package br.com.amisahdev.meetAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "event_subscription_activity",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subscription_id", "activity_id"})
        })
@Getter
@Setter
@NoArgsConstructor
public class EventSubscriptionActivityEntity extends AbstractEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private EventSubscriptionEntity subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityEntity activity;

    private Boolean attended;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", unique = true)
    private CertificateEntity certificate;
}
