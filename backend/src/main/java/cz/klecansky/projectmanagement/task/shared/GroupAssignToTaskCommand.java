package cz.klecansky.projectmanagement.task.shared;

import java.io.Serializable;
import java.util.List;

public record GroupAssignToTaskCommand(List<UserAssignToTaskCommand> userAssignToTaskCommandList)
        implements Serializable {}
