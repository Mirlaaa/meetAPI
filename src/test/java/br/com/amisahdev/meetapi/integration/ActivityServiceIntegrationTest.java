package br.com.amisahdev.meetapi.integration;

import br.com.amisahdev.meetapi.dto.request.ActivityRequest;
import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.model.EventEntity;
import br.com.amisahdev.meetapi.repository.ActivityEntityRepository;
import br.com.amisahdev.meetapi.repository.EventRepository;
import br.com.amisahdev.meetapi.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ActivityServiceIntegrationTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ActivityEntityRepository activityRepository;

    private EventEntity eventEntity;
    private UUID eventId;

    @BeforeEach
    void setup() {
        eventEntity = EventEntity.builder()
                .title("Integration Event")
                .build();

        eventEntity = eventRepository.save(eventEntity);
        eventId = eventEntity.getId();
    }

    @Test
    void shouldSaveActivity() {
        final ActivityRequest request = new ActivityRequest(
                "Integration Activity",
                "Integration Desc",
                eventId
        );

        final ActivityResponse response = activityService.save(request);

        assertNotNull(response);
        assertEquals("Integration Activity", response.title());
        assertEquals(eventId, response.eventId());

        final var saved = activityRepository.findById(response.id());
        assertTrue(saved.isPresent());
        assertEquals("Integration Activity", saved.get().getTitle());
    }

    @Test
    void shouldFindAllByEventId() {
        final ActivityRequest request = new ActivityRequest(
                "Activity 1",
                "Desc",
                eventId
        );
        activityService.save(request);

        final List<ActivityResponse> activities = activityService.findAllByEventId(eventId);
        assertEquals(1, activities.size());
        assertEquals("Activity 1", activities.get(0).title());
    }

    @Test
    void shouldThrowExceptionForInvalidEvent() {
        final ActivityRequest request = new ActivityRequest(
                "Activity X",
                "Desc",
                UUID.randomUUID()
        );

        assertThrows(RuntimeException.class, () -> activityService.save(request));
    }
}
