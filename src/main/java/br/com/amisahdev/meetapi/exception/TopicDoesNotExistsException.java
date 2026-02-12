package br.com.amisahdev.meetapi.exception;

public class TopicDoesNotExistsException extends BusinessException {
    public TopicDoesNotExistsException() {
        super("Topic not found");
    }
}
