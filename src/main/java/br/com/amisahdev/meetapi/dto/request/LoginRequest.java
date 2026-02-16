package br.com.amisahdev.meetapi.dto.request;

public record LoginRequest(
        String email,
        String password
) { }
