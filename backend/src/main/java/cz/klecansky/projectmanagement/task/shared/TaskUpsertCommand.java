package cz.klecansky.projectmanagement.task.shared;

import java.time.Instant;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.lang.Nullable;

public record TaskUpsertCommand(
        @NonNull UUID id,
        String name,
        String description,
        Instant startDate,
        Instant endDate,
        @NonNull Priority priority,
        Status status,
        @NonNull Integer progress,
        @NonNull UUID project,
        @Nullable UUID assigned,
        @Nullable UUID assignedForGroup,
        @Nullable UUID phase) {}
