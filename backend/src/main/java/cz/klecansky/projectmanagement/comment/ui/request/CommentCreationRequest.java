package cz.klecansky.projectmanagement.comment.ui.request;

import jakarta.validation.constraints.NotNull;

public record CommentCreationRequest(@NotNull String text) {}
