package br.com.amisahdev.meetAPI.dto.request;

public record LoginRequest(
        String email,
        String password
) {}
