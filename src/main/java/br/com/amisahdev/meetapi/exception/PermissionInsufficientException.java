package br.com.amisahdev.meetapi.exception;

public class PermissionInsufficientException extends BusinessException {
    public PermissionInsufficientException() {
        super("Permission Insufficient");
    }
}
