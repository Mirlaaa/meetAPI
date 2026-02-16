package br.com.amisahdev.meetapi.unit;


import br.com.amisahdev.meetapi.dto.request.EventRequest;
import br.com.amisahdev.meetapi.dto.response.EventResponse;
import br.com.amisahdev.meetapi.exception.BusinessException;
import br.com.amisahdev.meetapi.exception.PermissionInsufficientException;
import br.com.amisahdev.meetapi.mapper.EventMapper;
import br.com.amisahdev.meetapi.model.*;
import br.com.amisahdev.meetapi.repository.EventRepository;
import br.com.amisahdev.meetapi.repository.UserRepository;
import br.com.amisahdev.meetapi.service.EventService;
import br.com.amisahdev.meetapi.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private EventService eventService;

    private UserEntity organizer;
    private EventEntity event;
    private EventResponse eventResponse;
    private UUID eventId;
    private UUID organizerId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        organizerId = UUID.randomUUID();
        eventId = UUID.randomUUID();

        organizer = UserEntity.builder()
                .id(organizerId)
                .keycloakUserId(UUID.randomUUID())
                .email("organizer@test.com")
                .build();

        event = EventEntity.builder()
                .id(eventId)
                .title("Test Event")
                .description("Description")
                .active(true)
                .topics(new ArrayList<>())
                .organizations(new ArrayList<>())
                .build();

        final EventOrganizationEntity org = EventOrganizationEntity.builder()
                .event(event)
                .organizer(organizer)
                .build();

        event.getOrganizations().add(org);

        eventResponse = new EventResponse(eventId, "Test Event", "Description", true, Instant.now());
    }

    @Test
    void shouldFindEventById() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.map(event)).thenReturn(eventResponse);

        final EventResponse result = eventService.getEventById(eventId);

        assertEquals(eventResponse, result);
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void shouldThrowWhenEventNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void shouldFindAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(event));
        when(eventMapper.map(event)).thenReturn(eventResponse);

        final List<EventResponse> result = eventService.findAll();

        assertEquals(1, result.size());
        assertEquals(eventResponse, result.get(0));
    }

    @Test
    void shouldSaveEvent() {
        final EventRequest request = new EventRequest("Title", "Desc", "Java,Spring");

        final TopicEntity topic1 = TopicEntity.builder().title("java").build();
        final TopicEntity topic2 = TopicEntity.builder().title("spring").build();

        when(userRepository.getReferenceById(organizerId)).thenReturn(organizer);
        when(topicService.save("Java")).thenReturn(topic1);
        when(topicService.save("Spring")).thenReturn(topic2);

        when(eventRepository.save(any(EventEntity.class))).thenReturn(event);
        when(eventMapper.map(event)).thenReturn(eventResponse);

        final EventResponse result = eventService.save(request, organizerId);

        assertEquals(eventResponse, result);
        verify(eventRepository, times(1)).save(any(EventEntity.class));
    }

    @Test
    void shouldDeleteEventIfOrganizer() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.map(event)).thenReturn(eventResponse);

        final EventResponse result = eventService.delete(eventId, organizerId);

        assertEquals(eventResponse, result);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void shouldThrowIfNotOrganizerOnDelete() {
        final UUID otherUser = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertThrows(PermissionInsufficientException.class, () -> eventService.delete(eventId, otherUser));
        verify(eventRepository, never()).delete(any());
    }

    @Test
    void shouldThrowIfEventNotFoundOnDelete() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> eventService.delete(eventId, organizerId));
    }
}
