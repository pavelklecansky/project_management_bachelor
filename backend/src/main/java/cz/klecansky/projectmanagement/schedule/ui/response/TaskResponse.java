package cz.klecansky.projectmanagement.schedule.ui.response;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskResponse {

    private UUID realId;

    private int id;

    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;
}
