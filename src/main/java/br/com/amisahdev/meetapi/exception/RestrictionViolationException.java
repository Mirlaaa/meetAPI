package br.com.amisahdev.meetapi.exception;

public class RestrictionViolationException extends BusinessException {
    public RestrictionViolationException(final String message) {
        super(message);
    }
}
