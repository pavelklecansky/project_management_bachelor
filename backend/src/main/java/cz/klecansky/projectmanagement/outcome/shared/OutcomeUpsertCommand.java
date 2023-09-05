package cz.klecansky.projectmanagement.outcome.shared;

import java.time.Instant;
import java.util.UUID;
import org.springframework.lang.Nullable;

public record OutcomeUpsertCommand(
        @Nullable UUID id,
        String name,
        Instant startDate,
        Instant endDate,
        String description,
        UUID phaseId,
        @Nullable UUID outcomeCategoryId) {}
