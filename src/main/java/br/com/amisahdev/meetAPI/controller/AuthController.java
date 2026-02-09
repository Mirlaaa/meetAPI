package br.com.amisahdev.meetAPI.controller;

import br.com.amisahdev.meetAPI.dto.request.LoginRequest;
import br.com.amisahdev.meetAPI.dto.response.AuthResponse;
import br.com.amisahdev.meetAPI.dto.response.UserResponse;
import br.com.amisahdev.meetAPI.repository.UserRepository;
import br.com.amisahdev.meetAPI.security.JwtService;
import br.com.amisahdev.meetAPI.service.AuthService;
import br.com.amisahdev.meetAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
//            @RequestBody RefreshTokenRequest request,
//            HttpServletRequest httpRequest
//    ) {
//
//        Claims claims = jwtService.extractClaims(request.refreshToken());
//
//        String username = claims.getSubject();
//
//        UserEntity user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String newAccessToken = jwtService.generateToken(user);
//
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        new AuthResponse(newAccessToken, request.refreshToken()),
//                        "Token refreshed",
//                        httpRequest.getRequestURI()
//                )
//        );
//    }
}
