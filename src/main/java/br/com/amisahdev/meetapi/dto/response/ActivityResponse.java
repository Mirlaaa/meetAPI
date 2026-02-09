package br.com.amisahdev.meetapi.dto.response;

import java.util.UUID;

public record ActivityResponse(
        UUID id,
        String title,
        String description,
        Boolean active,
        UUID eventId
) { }
