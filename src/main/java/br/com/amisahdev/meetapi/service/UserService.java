package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.UserRequest;
import br.com.amisahdev.meetapi.dto.response.UserResponse;
import br.com.amisahdev.meetapi.exception.EmailAlreadyExistsException;
import br.com.amisahdev.meetapi.exception.UserNotFoundException;
import br.com.amisahdev.meetapi.mapper.UserMapper;
import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findById(final UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::map)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserResponse createUser(final UserRequest userRequest) {
        final Optional<UserEntity> emailAlreadyInUse = userRepository.findByEmail(userRequest.email());

        if (emailAlreadyInUse.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        final UserEntity user = UserEntity.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .active(true)
                .password(passwordEncoder.encode(userRequest.password()))
                .build();

        return userMapper.map(userRepository.save(user));
    }

    @Transactional
    public UserResponse delete(final UUID userId) {
        final UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setActive(false);

        return userMapper.map(userRepository.save(user));
    }
}
