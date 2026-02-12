package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.response.EventSubscriptionResponse;
import br.com.amisahdev.meetapi.model.EventSubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = EventSubscriptionActivityMapper.class
)
public interface EventSubscriptionMapper {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "event.id", target = "eventId")
    EventSubscriptionResponse map(EventSubscriptionEntity eventSubscriptionEntity);
}
