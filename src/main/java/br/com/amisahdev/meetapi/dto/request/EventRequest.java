package br.com.amisahdev.meetapi.dto.request;

public record EventRequest(
    String title,
    String description,
    String topic
) {
}
