package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.response.ActivityResponse;
import br.com.amisahdev.meetapi.model.ActivityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityResponse map(ActivityEntity activityEntity);
}
