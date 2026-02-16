package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.request.LoginRequest;
import br.com.amisahdev.meetapi.dto.request.RefreshTokenRequest;
import br.com.amisahdev.meetapi.dto.response.AuthResponse;
import br.com.amisahdev.meetapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login/")
    public ResponseEntity<AuthResponse> login(
            final @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("refresh/")
        public ResponseEntity<AuthResponse> refresh(
        final @RequestBody RefreshTokenRequest request) {
            return ResponseEntity.ok(authService.refreshToken(request));
    }
}
