package br.com.amisahdev.meetapi.unit;

import br.com.amisahdev.meetapi.dto.request.ActivityRequest;
import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.exception.EventNotFoundException;
import br.com.amisahdev.meetapi.mapper.ActivityMapper;
import br.com.amisahdev.meetapi.model.ActivityEntity;
import br.com.amisahdev.meetapi.model.EventEntity;
import br.com.amisahdev.meetapi.repository.ActivityEntityRepository;
import br.com.amisahdev.meetapi.repository.EventRepository;
import br.com.amisahdev.meetapi.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class ActivityServiceTest {

    @Mock
    private ActivityEntityRepository activityRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityService activityService;

    private UUID eventId;
    private EventEntity eventEntity;
    private ActivityEntity activityEntity;
    private ActivityRequest activityRequest;
    private ActivityResponse activityResponse;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        eventId = UUID.randomUUID();
        eventEntity = EventEntity.builder().id(eventId).title("Event 1").build();

        final UUID activityId = UUID.randomUUID();
        activityEntity = ActivityEntity.builder()
                .id(activityId)
                .title("Activity 1")
                .description("Desc")
                .event(eventEntity)
                .active(true)
                .build();

        activityRequest = new ActivityRequest(
                "Activity 1",
                "Desc",
                eventId
        );

        activityResponse = new ActivityResponse(
                activityId,
                "Activity 1",
                "Desc",
                true,
                eventId
        );
    }

    @Test
    void shouldFindAllActivities() {
        when(activityRepository.findAll()).thenReturn(List.of(activityEntity));
        when(activityMapper.map(activityEntity)).thenReturn(activityResponse);

        final List<ActivityResponse> result = activityService.findAll();

        assertEquals(1, result.size());
        assertEquals(activityResponse, result.get(0));
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void shouldFindAllByEventId() {
        when(activityRepository.findAllByEventId(eventId)).thenReturn(List.of(activityEntity));
        when(activityMapper.map(activityEntity)).thenReturn(activityResponse);

        final List<ActivityResponse> result = activityService.findAllByEventId(eventId);

        assertEquals(1, result.size());
        assertEquals(activityResponse, result.get(0));
        verify(activityRepository, times(1)).findAllByEventId(eventId);
    }

    @Test
    void shouldSaveActivityWhenEventExists() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(activityMapper.map(activityEntity)).thenReturn(activityResponse);

        final ActivityResponse result = activityService.save(activityRequest);

        assertNotNull(result);
        assertEquals(activityResponse, result);
        verify(activityRepository, times(1)).save(any(ActivityEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenEventNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> activityService.save(activityRequest));
        verify(activityRepository, never()).save(any());
    }
}
