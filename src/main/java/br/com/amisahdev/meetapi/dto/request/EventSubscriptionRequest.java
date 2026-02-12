package br.com.amisahdev.meetapi.dto.request;

import java.util.List;
import java.util.UUID;

public record EventSubscriptionRequest(
        UUID eventID,
        List<UUID> activities
) {
}
