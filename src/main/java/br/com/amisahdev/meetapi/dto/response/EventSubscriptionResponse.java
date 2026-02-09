package br.com.amisahdev.meetapi.dto.response;

import java.util.List;
import java.util.UUID;

public record EventSubscriptionResponse(
    UUID id,
    UUID participantId,
    UUID eventId,
    List<EventSubscriptionActivityResponse> activities
) {
}
