package br.com.amisahdev.meetAPI.controller;

import br.com.amisahdev.meetAPI.dto.response.EventResponse;
import br.com.amisahdev.meetAPI.model.EventSubscriptionActivityEntity;
import br.com.amisahdev.meetAPI.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Tag(name = "event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/")
    public ResponseEntity<List<EventResponse>> getEvents() {
        return ResponseEntity.ok().body(eventService.findAll());
    }

    @GetMapping("/participant/{participant_id}")
    public ResponseEntity<List<EventSubscriptionActivityEntity>> findAllByParticipant(@RequestParam UUID participant_id) {
        return findAllByParticipant(participant_id);
    }
}
