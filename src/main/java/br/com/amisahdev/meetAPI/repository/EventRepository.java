package br.com.amisahdev.meetAPI.repository;

import br.com.amisahdev.meetAPI.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {


}
