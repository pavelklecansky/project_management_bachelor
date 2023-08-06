package cz.klecansky.projectmanagement.comment.ui.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull
    private String text;

    private Instant createdDate = Instant.now();
}
