package br.com.amisahdev.meetapi.security;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        UUID keycloakId,
        String email
) {
}
