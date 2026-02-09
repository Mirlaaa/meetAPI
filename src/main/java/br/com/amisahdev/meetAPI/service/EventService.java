package br.com.amisahdev.meetAPI.service;

import br.com.amisahdev.meetAPI.dto.response.EventResponse;
import br.com.amisahdev.meetAPI.mapper.EventMapper;
import br.com.amisahdev.meetAPI.model.EventEntity;
import br.com.amisahdev.meetAPI.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventResponse getEventById(UUID eventId){
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return eventMapper.toResponse(event);
    };

    public List<EventResponse> findAll() {
        List<EventEntity> events = eventRepository.findAll();

        return eventMapper.toResponse(events);

    }

//    public List<EventResponse> findAllByParticipant(UUID participant_id) {
//        return eventRepository.findBySubscriptions(participant_id);
//    }
}
