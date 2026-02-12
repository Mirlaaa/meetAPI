package br.com.amisahdev.meetapi.exception;

public class EmailAlreadyExistsException extends RestrictionViolationException {
    public EmailAlreadyExistsException() {
        super("Email already exists");
    }
}
