package br.com.amisahdev.meetapi.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("User not found");
    }
}
