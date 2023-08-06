package cz.klecansky.projectmanagement.comment.ui.response;

import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CommentResponse {

    private UUID id;

    @NotNull
    @Size(max = 1000)
    private String text;

    private Instant createdDate;

    private UserResponse user;
}
