package cz.klecansky.projectmanagement.phase.shared;

import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class PhaseCommand {

    private UUID id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String note;

    private List<TaskCommand> taskEntities;

    private List<OutcomeCommand> outcomes;
}
