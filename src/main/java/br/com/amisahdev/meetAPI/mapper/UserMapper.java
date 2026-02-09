package br.com.amisahdev.meetAPI.mapper;

import br.com.amisahdev.meetAPI.dto.response.UserResponse;
import br.com.amisahdev.meetAPI.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse map(UserEntity entity);
}