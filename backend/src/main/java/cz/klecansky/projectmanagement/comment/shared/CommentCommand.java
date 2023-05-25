package cz.klecansky.projectmanagement.comment.shared;

import cz.klecansky.projectmanagement.user.shared.UserCommand;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CommentCommand {

    private UUID id;

    private String text;

    private Instant createdDate;

    private UserCommand user;
}
