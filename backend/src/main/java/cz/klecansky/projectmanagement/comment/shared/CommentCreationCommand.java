package cz.klecansky.projectmanagement.comment.shared;

import java.util.UUID;

public record CommentCreationCommand(String text, UUID projectId, UUID userId) {}
