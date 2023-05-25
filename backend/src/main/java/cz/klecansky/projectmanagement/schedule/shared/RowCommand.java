package cz.klecansky.projectmanagement.schedule.shared;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RowCommand {

    private UUID realId;

    private int id;

    private String label;

    private ScheduleCommand schedule;

    private List<TaskCommand> tasks;
}
