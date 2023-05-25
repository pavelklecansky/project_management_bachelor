package cz.klecansky.projectmanagement.outcome.ui.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

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
