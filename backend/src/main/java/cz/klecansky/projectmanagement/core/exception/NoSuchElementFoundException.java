package cz.klecansky.projectmanagement.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException {
    public NoSuchElementFoundException() {
    }

    public NoSuchElementFoundException(String message) {
        super(message);
    }
}
