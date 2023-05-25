package cz.klecansky.projectmanagement.group.shared;

import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GroupCommand {
    private UUID id;
    private String name;
    private List<GroupMemberCommand> members;
    private List<ProjectCommand> projects;
    private List<TaskCommand> taskAssignedTo;
}
