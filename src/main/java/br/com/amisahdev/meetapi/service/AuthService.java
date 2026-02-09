package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.LoginRequest;
import br.com.amisahdev.meetapi.dto.request.RefreshTokenRequest;
import br.com.amisahdev.meetapi.dto.response.AuthResponse;
import br.com.amisahdev.meetapi.exception.BusinessException;
import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.repository.UserRepository;
import br.com.amisahdev.meetapi.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    public AuthResponse login(final LoginRequest loginRequest) {
        log.info("Login Request: {}", loginRequest);
        log.info("Login Email: {}", loginRequest.email());

        final UserEntity user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.isActive()) {
            throw new BusinessException("Account disabled");
        }
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new AuthResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }

    public AuthResponse refreshToken(final RefreshTokenRequest tokenRequest) {
        final Claims claims = jwtService.extractClaims(tokenRequest.refreshToken());

        final UUID userId = UUID.fromString(claims.getSubject());

        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        return new AuthResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }
}
