package cz.klecansky.projectmanagement.outcome.ui.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import org.springframework.lang.Nullable;

public record OutcomeUpsertRequest(
        @Nullable UUID id,
        String name,
        Instant startDate,
        Instant endDate,
        String description,
        @NotNull UUID phaseId,
        @Nullable UUID outcomeCategoryId) {}
