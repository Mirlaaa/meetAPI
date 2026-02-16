package br.com.amisahdev.meetapi.unit;


import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.UserRepository;
import br.com.amisahdev.meetapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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

        when(userRepository.findByKeycloakUserId(keycloakId))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        final UserEntity user = userService.getOrCreate(jwt);

        assertNotNull(user);
        assertEquals(keycloakId, user.getKeycloakUserId());
        assertEquals("test@email.com", user.getEmail());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldReturnExistingUser() {
        final UUID keycloakId = UUID.randomUUID();
        final UserEntity existing = UserEntity.builder()
                .keycloakUserId(keycloakId)
                .name("testuser")
                .email("email@test.com")
                .build();

        when(userRepository.findByKeycloakUserId(keycloakId))
                .thenReturn(Optional.of(existing));

        final UserEntity result = userService.getOrCreate(buildJwt(keycloakId));

        assertEquals(existing.getId(), result.getId());
        verify(userRepository, never()).save(any());
    }
}
