package br.com.amisahdev.meetapi.handler;

import br.com.amisahdev.meetapi.exception.EventNotFoundException;
import br.com.amisahdev.meetapi.exception.PermissionInsufficientException;
import br.com.amisahdev.meetapi.exception.RestrictionViolationException;
import br.com.amisahdev.meetapi.exception.TopicDoesNotExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayDeque;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    protected ResponseEntity<Object> buildErrorResponse(final String message, final HttpStatus status) {
        final var errorResponse = ErrorResponse.builder()
                .message(message)
                .code(status.value())
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(PermissionInsufficientException.class)
    public ResponseEntity<Object> handlePermissionInsufficientException(final PermissionInsufficientException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleNotFoundException(final ExpiredJwtException ex) {
        log.info("Token expired: {}", ex.getMessage());
        return buildErrorResponse("Token expired", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({TopicDoesNotExistsException.class, EventNotFoundException.class})
    public ResponseEntity<Object> handleDoesNotExistsException(final TopicDoesNotExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestrictionViolationException.class)
    public ResponseEntity<Object> handleRestrictionViolationException(final RestrictionViolationException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final var errorResponseBuilder = ErrorResponse.builder();
        errorResponseBuilder.code(HttpStatus.BAD_REQUEST.value());
        errorResponseBuilder.message("Validation error");
        errorResponseBuilder.fields(new ArrayDeque<>());

        final var errorResponse = errorResponseBuilder.build();

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            final ErrorField fieldError = ErrorField.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build();

            errorResponse.getFields().add(fieldError);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
