package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.request.ActivityRequest;
import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity/")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities() {
        return ResponseEntity.ok().body(activityService.findAll());
    }

    @GetMapping("event/{eventId}/")
    public ResponseEntity<List<ActivityResponse>> getActivitiesByEventId(final @PathVariable UUID eventId) {
        return ResponseEntity.ok().body(activityService.findAllByEventId(eventId));
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(final @Valid @RequestBody ActivityRequest activityRequest) {
        return ResponseEntity.ok().body(activityService.save(activityRequest));
    }
}
