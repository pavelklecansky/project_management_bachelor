package cz.klecansky.projectmanagement.core.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessResponse<T> implements Response {
    private String message;
    private T data;
}
