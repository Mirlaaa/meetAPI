package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.request.TopicRequest;
import br.com.amisahdev.meetapi.dto.response.TopicResponse;
import br.com.amisahdev.meetapi.service.TopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
@Tag(name = "topic")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/")
    public ResponseEntity<List<TopicResponse>> getTopics() {
        return ResponseEntity.ok(topicService.findAll());
    }

    @GetMapping("/{topicId}/")
    public ResponseEntity<TopicResponse> getTopic(final @PathVariable UUID topicId) {
        return ResponseEntity.ok(topicService.findById(topicId));
    }

    @PostMapping("/")
    public ResponseEntity<TopicResponse> createTopic(final @RequestBody TopicRequest topicRequest) {
        return ResponseEntity.ok(topicService.save(topicRequest));
    }

    @PutMapping("/{topicId}/")
    public ResponseEntity<TopicResponse> updateTopic(final @RequestBody TopicRequest topicRequest, final @PathVariable UUID topicId) {
        return ResponseEntity.ok(topicService.update(topicId, topicRequest));
    }
}
