package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.EventSubscriptionRequest;
import br.com.amisahdev.meetapi.dto.response.EventSubscriptionResponse;
import br.com.amisahdev.meetapi.exception.BusinessException;
import br.com.amisahdev.meetapi.exception.EventNotFoundException;
import br.com.amisahdev.meetapi.exception.PermissionInsufficientException;
import br.com.amisahdev.meetapi.exception.UserNotFoundException;
import br.com.amisahdev.meetapi.mapper.EventSubscriptionMapper;
import br.com.amisahdev.meetapi.model.EventEntity;
import br.com.amisahdev.meetapi.model.EventSubscriptionActivityEntity;
import br.com.amisahdev.meetapi.model.EventSubscriptionEntity;
import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.ActivityEntityRepository;
import br.com.amisahdev.meetapi.repository.EventRepository;
import br.com.amisahdev.meetapi.repository.EventSubscriptionRepository;
import br.com.amisahdev.meetapi.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.of;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventSubscriptionService {

    private final EventSubscriptionRepository eventSubscriptionRepository;
    private final EventSubscriptionMapper eventSubscriptionMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ActivityEntityRepository activityEntityRepository;

    public List<EventSubscriptionResponse> findAllByParticipant(final UUID participantId) {
        return of(eventSubscriptionRepository.findAllByActiveAndParticipantId(true, participantId))
                .orElse(new ArrayList<>())
                .stream()
                .map(eventSubscriptionMapper::map).toList();
    };
9
    @Transactional
    public EventSubscriptionResponse save(final EventSubscriptionRequest eventSubscriptionRequest, final UUID participantId) {
        final EventEntity event = eventRepository.findById(eventSubscriptionRequest.eventID())
                .orElseThrow(EventNotFoundException::new);

        final UserEntity participant = userRepository.findById(participantId)
                .orElseThrow(UserNotFoundException::new);

        final EventSubscriptionEntity subscription =
                EventSubscriptionEntity.builder()
                        .event(event)
                        .active(true)
                        .participant(participant)
                        .build();


        final List<EventSubscriptionActivityEntity> activities = eventSubscriptionRequest.activities()
                .stream()
                .map(activity -> {
                    final EventSubscriptionActivityEntity activitySubscription = new EventSubscriptionActivityEntity();
                    activitySubscription.setSubscription(subscription);
                    activitySubscription.setAttended(false);
                    activitySubscription.setActivity(activityEntityRepository.getReferenceById(activity));
                    return activitySubscription;
                })
                .toList();

        subscription.setActivities(activities);
        return eventSubscriptionMapper.map(eventSubscriptionRepository.save(subscription));
    };

    @Transactional
    public EventSubscriptionResponse delete(final UUID subscriptionId, final UUID participantId) {
        final EventSubscriptionEntity subscription = eventSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException("Event subscription not found"));

        if (!subscription.getParticipant().getId().equals(participantId)) {
            throw new PermissionInsufficientException();
        }

        eventSubscriptionRepository.delete(subscription);
        return eventSubscriptionMapper.map(subscription);
    }
}
