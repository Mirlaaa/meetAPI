package br.com.amisahdev.meetapi.dto.response;

import java.util.Date;
import java.util.UUID;

public record EventResponse(
    UUID id,
    String title,
    String description,
    Boolean active,
    Date createdAt
) { }
