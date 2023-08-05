package cz.klecansky.projectmanagement.schedule.shared;

import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ScheduleCommand {

    private UUID id;

    private ProjectCommand project;

    private List<RowCommand> rows;

    private List<TaskCommand> tasks;
}
