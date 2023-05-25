package cz.klecansky.projectmanagement.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadPasscodeException extends RuntimeException {
    public BadPasscodeException(String message) {
        super(message);
    }
}
