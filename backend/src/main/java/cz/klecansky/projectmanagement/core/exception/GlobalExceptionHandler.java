package cz.klecansky.projectmanagement.core.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import cz.klecansky.projectmanagement.core.response.ErrorResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleStatusException(AuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(UNAUTHORIZED.value())
                        .status(UNAUTHORIZED.getReasonPhrase())
                        .message("Authorization error")
                        .build(),
                UNAUTHORIZED);
    }

    @Override
    @ResponseStatus(BAD_REQUEST)
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(BAD_REQUEST.value())
                        .status(BAD_REQUEST.getReasonPhrase())
                        .message("Bad type ")
                        .build(),
                BAD_REQUEST);
    }
}
