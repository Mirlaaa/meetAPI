package br.com.amisahdev.meetapi.integration;


import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.UserRepository;
import br.com.amisahdev.meetapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    private Jwt buildJwt(final UUID sub) {
        return Jwt.withTokenValue("fake-token")
                .header("alg", "none")
                .claim("sub", sub.toString())
                .claim("email", "test@email.com")
                .claim("preferred_username", "testuser")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();
    }

    @Test
    void shouldCreateUserIfNotExists() {
        final UUID keycloakId = UUID.randomUUID();
        final Jwt jwt = buildJwt(keycloakId);

        final UserEntity user = userService.getOrCreate(jwt);

        assertNotNull(user.getId(), "ID deve ser gerado pelo banco");
        assertEquals(keycloakId, user.getKeycloakUserId());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("test@email.com", user.getUsername());

        final Optional<UserEntity> fromDb = repository.findById(user.getId());
        assertTrue(fromDb.isPresent());
    }

    @Test
    void shouldReturnExistingUser() {
        final UUID keycloakId = UUID.randomUUID();

        final UserEntity existing = repository.save(
                UserEntity.builder()
                        .keycloakUserId(keycloakId)
                        .email("existing@email.com")
                        .build()
        );

        final Jwt jwt = buildJwt(keycloakId);

        final UserEntity user = userService.getOrCreate(jwt);

        assertEquals(existing.getId(), user.getId(), "Deve retornar o usuário existente");
        assertEquals("existing@email.com", user.getEmail());
    }
}
