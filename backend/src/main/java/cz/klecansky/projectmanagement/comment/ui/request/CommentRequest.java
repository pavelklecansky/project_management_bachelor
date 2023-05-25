package cz.klecansky.projectmanagement.comment.ui.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class CommentRequest {

    @NotNull private String text;

    private Instant createdDate = Instant.now();
}
