package cz.klecansky.projectmanagement.task.ui.request;

import cz.klecansky.projectmanagement.task.shared.Priority;
import cz.klecansky.projectmanagement.task.shared.Status;
import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class TaskRequest {

    @NotBlank
    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    @NotNull
    private Priority priority = Priority.NORMAL;

    private Status status = Status.NEW;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer progress = 0;

    @Nullable
    private UUID project;

    @Nullable
    private UUID assigned;

    @Nullable
    private UUID assignedForGroup;

    @Nullable
    private UUID phase;
}
