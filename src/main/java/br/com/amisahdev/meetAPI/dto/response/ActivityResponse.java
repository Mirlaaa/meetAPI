package br.com.amisahdev.meetAPI.dto.response;

import java.util.UUID;

public record ActivityResponse(
        UUID id,
        String title,
        String description,
        Boolean active
) {}
