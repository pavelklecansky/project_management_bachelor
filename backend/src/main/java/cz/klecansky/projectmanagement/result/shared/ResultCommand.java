package cz.klecansky.projectmanagement.result.shared;

import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import java.util.UUID;
import lombok.Data;

@Data
public class ResultCommand {

    private UUID id;

    private String name;

    private String description;

    private OutcomeCommand outcome;
}
