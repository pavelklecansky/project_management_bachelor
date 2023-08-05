package cz.klecansky.projectmanagement.budget.shared;

import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BudgetCommand {

    private UUID id;

    private ProjectCommand project;

    private List<BudgetCategoryCommand> budgetCategories;
}
