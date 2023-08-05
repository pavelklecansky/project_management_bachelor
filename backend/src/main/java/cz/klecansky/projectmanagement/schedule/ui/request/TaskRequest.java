package cz.klecansky.projectmanagement.schedule.ui.request;

import java.time.Instant;
import lombok.Data;

@Data
public class TaskRequest {
    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;
}
