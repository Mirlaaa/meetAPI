package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    List<EventEntity> findDistinctByOrganizationsOrganizerId(UUID organizerId);
}
