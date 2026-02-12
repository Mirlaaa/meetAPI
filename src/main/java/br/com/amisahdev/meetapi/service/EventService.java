package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.EventRequest;
import br.com.amisahdev.meetapi.dto.response.EventResponse;
import br.com.amisahdev.meetapi.exception.BusinessException;
import br.com.amisahdev.meetapi.exception.PermissionInsufficientException;
import br.com.amisahdev.meetapi.mapper.EventMapper;
import br.com.amisahdev.meetapi.model.EventEntity;
import br.com.amisahdev.meetapi.model.EventOrganizationEntity;
import br.com.amisahdev.meetapi.model.TopicEntity;
import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.EventRepository;
import br.com.amisahdev.meetapi.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.of;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final TopicService topicService;


    public EventResponse getEventById(final UUID eventId) {
        final EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return eventMapper.map(event);
    }

    public List<EventResponse> findAll() {
        return of(eventRepository.findAll())
                .orElse(new ArrayList<>())
                .stream()
                .map(eventMapper::map).toList();
    }

    public List<EventResponse> findAllByOrganizer(final UUID organizerId) {
        return of(eventRepository.findDistinctByOrganizationsOrganizerId(organizerId))
                .orElse(new ArrayList<>())
                .stream()
                .map(eventMapper::map).toList();
    }

    @Transactional
    public EventResponse save(final EventRequest eventRequest, final UUID organizerId) {
        final UserEntity eventOrganizer = userRepository.getReferenceById(organizerId);

        final List<TopicEntity> topics = Arrays.stream(eventRequest.topic().split(","))
                .map(topicService::save)
                .toList();

        final EventEntity event = EventEntity.builder()
                .title(eventRequest.title())
                .description(eventRequest.description())
                .active(true)
                .build();


        final EventOrganizationEntity eventOrganization = EventOrganizationEntity.builder()
                .organizer(eventOrganizer)
                .event(event)
                .build();

        event.getTopics().addAll(topics);
        event.getOrganizations().add(eventOrganization);
        return eventMapper.map(eventRepository.save(event));
    }

    @Transactional
    public EventResponse delete(final UUID eventId, final UUID organizerId) {
        final EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BusinessException("Event not found"));

        final boolean isOrganizer = event.getOrganizations().stream()
                .anyMatch(org -> org.getOrganizer().getId().equals(organizerId));

        if (!isOrganizer) {
            throw new PermissionInsufficientException();
        }
        eventRepository.delete(event);

        return eventMapper.map(event);
    }

}