package cz.klecansky.projectmanagement.comment.shared;

import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CommentCommand {

    private UUID id;

    private String text;

    private Instant createdDate;

    private UserCommand user;
}
