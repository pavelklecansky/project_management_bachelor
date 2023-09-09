package cz.klecansky.projectmanagement.project.ui.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import org.springframework.lang.Nullable;

public record ProjectUpsertRequest(
        @Nullable UUID id,
        @NotNull String name,
        @NotNull String description,
        @NotNull Instant startDate,
        @NotNull Instant endDate) {}
