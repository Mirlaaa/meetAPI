package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.response.EventSubscriptionActivityResponse;
import br.com.amisahdev.meetapi.model.EventSubscriptionActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventSubscriptionActivityMapper {

    @Mapping(source = "subscription.id", target = "subscriptionId")
    @Mapping(source = "activity.id", target = "activityId")
    EventSubscriptionActivityResponse map(EventSubscriptionActivityEntity eventSubscriptionActivityEntity);
}
