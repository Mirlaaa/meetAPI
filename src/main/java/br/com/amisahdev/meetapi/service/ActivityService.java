package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.ActivityRequest;
import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.exception.EventNotFoundException;
import br.com.amisahdev.meetapi.mapper.ActivityMapper;
import br.com.amisahdev.meetapi.model.ActivityEntity;
import br.com.amisahdev.meetapi.model.EventEntity;
import br.com.amisahdev.meetapi.repository.ActivityEntityRepository;
import br.com.amisahdev.meetapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityEntityRepository activityEntityRepository;
    private final ActivityMapper activityMapper;
    private final EventRepository eventRepository;

    public List<ActivityResponse> findAll() {
        return activityEntityRepository.findAll()
                .stream()
                .map(activityMapper::map)
                .toList();
    }

    public List<ActivityResponse> findAllByEventId(final UUID eventId) {
        return activityEntityRepository.findAllByEventId(eventId)
                .stream()
                .map(activityMapper::map)
                .toList();
    }

    @Transactional
    public ActivityResponse save(final ActivityRequest activityRequest) {
        final EventEntity eventEntity = eventRepository.findById(activityRequest.eventId())
                .orElseThrow(EventNotFoundException::new);

        final ActivityEntity activity = ActivityEntity.builder()
                .active(true)
                .title(activityRequest.title())
                .description(activityRequest.description())
                .event(eventEntity)
                .build();

        activityEntityRepository.save(activity);
        return activityMapper.map(activity);
    }
}
