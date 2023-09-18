package cz.klecansky.projectmanagement.task.shared;

import java.io.Serializable;
import java.util.UUID;

public record UserAssignToTaskCommand(UUID taskId, String taskName, String toEmail) implements Serializable {}
