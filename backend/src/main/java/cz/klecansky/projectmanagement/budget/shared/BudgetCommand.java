package cz.klecansky.projectmanagement.budget.shared;


import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BudgetCommand {

    private UUID id;

    private ProjectCommand project;

    private List<BudgetCategoryCommand> budgetCategories;
}
