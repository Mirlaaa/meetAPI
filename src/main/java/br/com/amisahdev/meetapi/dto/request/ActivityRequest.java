package br.com.amisahdev.meetapi.dto.request;

import java.util.UUID;

public record ActivityRequest(
        String title,
        String description,
        UUID eventId
) { }
