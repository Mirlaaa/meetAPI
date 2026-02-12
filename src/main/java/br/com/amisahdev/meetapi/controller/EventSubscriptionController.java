package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.request.EventSubscriptionRequest;
import br.com.amisahdev.meetapi.dto.response.EventResponse;
import br.com.amisahdev.meetapi.dto.response.EventSubscriptionResponse;
import br.com.amisahdev.meetapi.security.AuthenticatedUser;
import br.com.amisahdev.meetapi.service.EventSubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event/subscription/")
@RequiredArgsConstructor
@Tag(name = "event-subscription")
public class EventSubscriptionController {

    private final EventSubscriptionService eventSubscriptionService;

    @PostMapping
    public ResponseEntity<EventSubscriptionResponse> createEventSubscription(final @RequestBody EventSubscriptionRequest eventSubscriptionRequest,
                                                                             final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(eventSubscriptionService.save(eventSubscriptionRequest, userPrincipal.id()));
    }

    @GetMapping
    public ResponseEntity<List<EventSubscriptionResponse>> findAllEventSubscriptionsByParticipant(final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(eventSubscriptionService.findAllByParticipant(userPrincipal.id()));
    }

    @DeleteMapping("/{eventSub}/")
    public ResponseEntity<EventSubscriptionResponse> deleteEventSubscription(final @PathVariable UUID eventSub,
                                                     final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(eventSubscriptionService.delete(eventSub, userPrincipal.id()));
    }
}
