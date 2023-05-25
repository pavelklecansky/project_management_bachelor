package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.result.shared.ResultCommand;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class OutcomeCommand {

    private UUID id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String description;

    private PhaseCommand phase;

    private OutcomeCategoryCommand outcomeCategory;

    private List<ResultCommand> results;
}
