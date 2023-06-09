package cz.klecansky.projectmanagement.comment.ui.response;

import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Data
public class CommentResponse {

    private UUID id;
    @NotNull
    @Size(max = 1000)
    private String text;

    private Instant createdDate;

    private UserResponse user;
}
