package br.com.amisahdev.meetAPI.dto.response;

import java.util.UUID;

public record OrganizerResponse(
        UUID id,
        String name,
        String username
) {}