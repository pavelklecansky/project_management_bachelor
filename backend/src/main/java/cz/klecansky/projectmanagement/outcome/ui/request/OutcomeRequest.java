package cz.klecansky.projectmanagement.outcome.ui.request;

import java.time.Instant;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OutcomeRequest {

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String description;

    @NotNull
    private UUID phase;

    private UUID outcomeCategory;
}
