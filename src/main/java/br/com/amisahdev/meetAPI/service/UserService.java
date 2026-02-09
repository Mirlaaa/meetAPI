package br.com.amisahdev.meetAPI.service;

import br.com.amisahdev.meetAPI.dto.request.UserRequest;
import br.com.amisahdev.meetAPI.dto.response.UserResponse;
import br.com.amisahdev.meetAPI.mapper.UserMapper;
import br.com.amisahdev.meetAPI.model.Role;
import br.com.amisahdev.meetAPI.model.UserEntity;
import br.com.amisahdev.meetAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public Optional<UserResponse> findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::map);
    }


    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        UserEntity user = UserEntity.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(Role.ADMIN).build();

        return userMapper.map(userRepository.save(user));
    }
}
