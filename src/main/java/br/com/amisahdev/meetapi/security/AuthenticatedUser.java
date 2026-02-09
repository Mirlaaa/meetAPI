package br.com.amisahdev.meetapi.security;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email
) {
}
