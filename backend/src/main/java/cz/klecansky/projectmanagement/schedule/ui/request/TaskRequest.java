package cz.klecansky.projectmanagement.schedule.ui.request;

import lombok.Data;

import java.time.Instant;

@Data
public class TaskRequest {
    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;
}
