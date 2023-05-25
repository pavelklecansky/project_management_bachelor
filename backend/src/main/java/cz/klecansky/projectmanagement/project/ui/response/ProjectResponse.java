package cz.klecansky.projectmanagement.project.ui.response;

import cz.klecansky.projectmanagement.comment.ui.response.CommentResponse;
import cz.klecansky.projectmanagement.group.ui.response.GroupResponse;
import cz.klecansky.projectmanagement.task.ui.response.TaskResponse;
import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectResponse {
    private UUID id;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private List<TaskResponse> tasks;

    private List<CommentResponse> comments;

    private List<UserResponse> members;

    private List<GroupResponse> memberGroups;
}
