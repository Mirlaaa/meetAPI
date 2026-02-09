package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, UUID> {
    List<ActivityEntity> findAllByEventId(UUID eventId);
}
