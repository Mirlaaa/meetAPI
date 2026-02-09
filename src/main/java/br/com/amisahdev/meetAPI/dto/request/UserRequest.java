package br.com.amisahdev.meetAPI.dto.request;

public record UserRequest(
        String username,
        String name,
        String password,
        String email
) {}
