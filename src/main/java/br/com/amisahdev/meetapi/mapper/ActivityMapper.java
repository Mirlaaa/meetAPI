package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.model.ActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    @Mapping(source = "event.id", target = "eventId")
    ActivityResponse map(ActivityEntity activityEntity);
}
