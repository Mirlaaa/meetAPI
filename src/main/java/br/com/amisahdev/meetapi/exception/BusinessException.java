package br.com.amisahdev.meetapi.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }
}
