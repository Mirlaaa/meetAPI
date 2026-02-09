package br.com.amisahdev.meetAPI.mapper;

import br.com.amisahdev.meetAPI.dto.response.EventResponse;
import br.com.amisahdev.meetAPI.model.EventEntity;
import com.sun.jdi.request.EventRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventEntity toEntity(EventRequest eventRequest);
    EventResponse toResponse(EventEntity eventEntity);
    List<EventResponse> toResponse(List<EventEntity> events);
}
