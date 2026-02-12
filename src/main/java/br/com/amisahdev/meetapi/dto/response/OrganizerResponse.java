package br.com.amisahdev.meetapi.dto.response;

import java.util.UUID;

public record OrganizerResponse(
        UUID id,
        String name,
        String username
) { }