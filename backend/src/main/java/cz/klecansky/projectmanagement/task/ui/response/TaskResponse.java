package cz.klecansky.projectmanagement.task.ui.response;

import cz.klecansky.projectmanagement.comment.ui.response.CommentResponse;
import cz.klecansky.projectmanagement.group.ui.response.GroupResponse;
import cz.klecansky.projectmanagement.phase.ui.response.PhaseResponse;
import cz.klecansky.projectmanagement.task.shared.Priority;
import cz.klecansky.projectmanagement.task.shared.Status;
import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class TaskResponse {
    private UUID id;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Priority priority;

    private Status status;

    private Integer progress;

    private UserResponse assigned;

    private GroupResponse assignedForGroup;

    private PhaseResponse phase;

    private List<CommentResponse> comments;
}
