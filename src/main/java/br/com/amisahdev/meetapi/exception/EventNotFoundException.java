package br.com.amisahdev.meetapi.exception;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException() {
        super("Event not found");
    }
}
