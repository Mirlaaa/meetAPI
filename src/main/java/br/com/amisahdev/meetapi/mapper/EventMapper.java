package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.request.EventRequest;
import br.com.amisahdev.meetapi.dto.response.EventResponse;
import br.com.amisahdev.meetapi.model.EventEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EventMapper {
    EventEntity toEntity(EventRequest eventRequest);

    EventResponse map(EventEntity eventEntity);
}
