package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<TopicEntity, UUID> {
    Optional<TopicEntity> findByTitle(String title);

    Optional<TopicEntity> findByTitleEqualsIgnoreCase(String title);

    Optional<TopicEntity> findByTitleAndActive(String title, boolean active);
}
