package br.com.amisahdev.meetAPI.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
