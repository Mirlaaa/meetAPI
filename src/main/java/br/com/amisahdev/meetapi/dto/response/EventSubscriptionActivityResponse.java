package br.com.amisahdev.meetapi.dto.response;

import java.util.UUID;

public record EventSubscriptionActivityResponse(
        UUID subscriptionId,
        UUID activityId,
        boolean attended
) {
}
