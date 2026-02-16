package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.EventSubscriptionActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventSubscriptionActivityRepository extends JpaRepository<EventSubscriptionActivityEntity, UUID> {
}
