package cz.klecansky.projectmanagement.comment.ui.request;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull
    private String text;

    private Instant createdDate = Instant.now();
}
