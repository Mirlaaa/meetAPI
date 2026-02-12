package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.request.EventRequest;
import br.com.amisahdev.meetapi.dto.response.EventResponse;
import br.com.amisahdev.meetapi.security.AuthenticatedUser;
import br.com.amisahdev.meetapi.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event/")
@RequiredArgsConstructor
@Tag(name = "event")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents() {
        return ResponseEntity.ok().body(eventService.findAll());
    }

    @GetMapping("/organized/{organizerId}")
    public ResponseEntity<List<EventResponse>> findAllEventSubscriptionsByOrganizer(final @PathVariable UUID organizerId) {
        return ResponseEntity.ok().body(eventService.findAllByOrganizer(organizerId));
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(final @Valid @RequestBody EventRequest eventRequest,
                                                     final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(eventService.save(eventRequest, userPrincipal.id()));
    }

    @DeleteMapping("{eventId}/")
    public ResponseEntity<EventResponse> deleteEvent(final @PathVariable UUID eventId,
                                                     final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(eventService.delete(eventId, userPrincipal.id()));
    }
}
