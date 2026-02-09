package br.com.amisahdev.meetAPI.dto.response;

public record UserResponse(
        String username,
        String name,
        String password,
        String email
) {}
