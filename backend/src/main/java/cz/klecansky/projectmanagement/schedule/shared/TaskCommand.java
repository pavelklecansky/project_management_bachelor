package cz.klecansky.projectmanagement.schedule.shared;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskCommand {

    private UUID realId;

    private int id;

    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;

    private ScheduleCommand schedule;

    private RowCommand row;
}
