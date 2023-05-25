package cz.klecansky.projectmanagement.group.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Group cannot be deleted")
public class GroupCannotBeDeletedException extends RuntimeException {

    public GroupCannotBeDeletedException(String message) {
        super(message);
    }

    public GroupCannotBeDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
