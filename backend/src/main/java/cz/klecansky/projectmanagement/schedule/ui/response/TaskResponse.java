package cz.klecansky.projectmanagement.schedule.ui.response;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskResponse {

    private UUID realId;

    private int id;

    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;
}
