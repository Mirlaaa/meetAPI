package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.EventOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventOrganizationEntityRepository extends JpaRepository<EventOrganizationEntity, UUID> {
}
