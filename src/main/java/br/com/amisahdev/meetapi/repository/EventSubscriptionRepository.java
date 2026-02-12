package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.EventSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventSubscriptionRepository extends JpaRepository<EventSubscriptionEntity, UUID> {
    List<EventSubscriptionEntity> findAllByParticipantId(UUID participant);
    List<EventSubscriptionEntity> findAllByActiveAndParticipantId(Boolean active, UUID participant);

}