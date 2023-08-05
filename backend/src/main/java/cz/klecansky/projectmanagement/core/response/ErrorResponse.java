package cz.klecansky.projectmanagement.core.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse implements Response {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private int code;

    private String status;

    private String message;

    private String stackTrace;

    private Object data;
}
