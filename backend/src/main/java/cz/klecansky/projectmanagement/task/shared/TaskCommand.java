package cz.klecansky.projectmanagement.task.shared;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class TaskCommand {

    private UUID id;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Priority priority;

    private Status status;

    private Integer progress;

    private List<CommentCommand> comments;

    @ToString.Exclude
    private ProjectCommand project;

    @ToString.Exclude
    @Nullable
    private UserCommand assigned;

    @ToString.Exclude
    @Nullable
    private GroupCommand assignedForGroup;

    @Nullable
    private PhaseCommand phase;
}
