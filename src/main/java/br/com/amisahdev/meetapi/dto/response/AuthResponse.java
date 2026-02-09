package br.com.amisahdev.meetapi.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) { }
