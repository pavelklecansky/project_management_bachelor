package cz.klecansky.projectmanagement.task.ui.request;

import cz.klecansky.projectmanagement.task.shared.Priority;
import cz.klecansky.projectmanagement.task.shared.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
public class TaskRequest {

    @Nullable
    UUID id;

    @NotBlank
    String name;

    String description;

    Instant startDate;

    Instant endDate;

    @NotNull
    Priority priority = Priority.NORMAL;

    Status status = Status.NEW;

    @Min(0)
    @Max(100)
    @NotNull
    Integer progress = 0;

    @NotNull
    UUID project;

    @Nullable
    UUID assigned;

    @Nullable
    UUID assignedForGroup;

    @Nullable
    UUID phase;
}
