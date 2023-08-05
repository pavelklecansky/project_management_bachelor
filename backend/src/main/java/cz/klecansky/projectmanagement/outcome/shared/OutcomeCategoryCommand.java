package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class OutcomeCategoryCommand {

    private UUID id;

    private String name;

    private String description;

    private ProjectCommand project;

    private List<OutcomeCommand> outcomes;
}
