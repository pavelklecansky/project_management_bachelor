package cz.klecansky.projectmanagement.core.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import cz.klecansky.projectmanagement.core.response.ErrorResponse;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleStatusException() {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(UNAUTHORIZED.value())
                        .status(UNAUTHORIZED.getReasonPhrase())
                        .message("Authorization error")
                        .build(),
                UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(Exception ex) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(BAD_REQUEST.value())
                        .status(BAD_REQUEST.getReasonPhrase())
                        .message(ex.getMessage())
                        .build(),
                BAD_REQUEST);
    }
}
