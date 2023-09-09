package cz.klecansky.projectmanagement.project.shared;

import java.time.Instant;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record ProjectUpsertCommand(
        @NonNull UUID id,
        @Nullable String name,
        @Nullable String description,
        @Nullable Instant startDate,
        @Nullable Instant endDate) {}
