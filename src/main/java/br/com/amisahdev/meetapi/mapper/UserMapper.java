package br.com.amisahdev.meetapi.mapper;

import br.com.amisahdev.meetapi.dto.response.UserResponse;
import br.com.amisahdev.meetapi.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse map(UserEntity entity);
}