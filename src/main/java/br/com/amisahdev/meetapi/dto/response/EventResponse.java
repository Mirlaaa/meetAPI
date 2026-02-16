package br.com.amisahdev.meetapi.dto.response;

import java.time.Instant;
import java.util.UUID;

public record EventResponse(
    UUID id,
    String title,
    String description,
    Boolean active,
    Instant createdAt
) { }
