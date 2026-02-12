package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(final String email);
    Optional<UserEntity> findById(final UUID id);
}
