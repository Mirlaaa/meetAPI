package br.com.amisahdev.meetAPI.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="certificate")
@Getter
@Setter
@NoArgsConstructor
public class CertificateEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "certificate", fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_activity_id",  nullable = false)
    private EventSubscriptionActivityEntity subscriptionActivity;

}
