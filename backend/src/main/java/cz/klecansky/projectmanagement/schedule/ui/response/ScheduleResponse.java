package cz.klecansky.projectmanagement.schedule.ui.response;


import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ScheduleResponse {

    private UUID id;

    private ProjectResponse project;

    private List<RowResponse> rows;

    private List<TaskResponse> tasks;
}
