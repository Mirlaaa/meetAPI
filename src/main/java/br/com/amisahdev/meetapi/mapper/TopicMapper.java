package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.request.TopicRequest;
import br.com.amisahdev.meetapi.dto.response.TopicResponse;
import br.com.amisahdev.meetapi.model.TopicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Named("toString")
    default String toString(TopicEntity topic) {
        return topic != null ? topic.toString() : null;
    }
    @Mapping(source = "topic", target = "title", qualifiedByName = "toString")
    TopicResponse map(TopicEntity topic);

    TopicEntity map(TopicRequest topicRequest);
}
