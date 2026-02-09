package br.com.amisahdev.meetapi.dto.response;

import java.util.UUID;

public record TopicResponse(
        UUID id,
        String title
) { }
