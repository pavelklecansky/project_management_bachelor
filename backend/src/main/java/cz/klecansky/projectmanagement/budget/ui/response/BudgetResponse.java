package cz.klecansky.projectmanagement.budget.ui.response;

import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BudgetResponse {

    private UUID id;

    private ProjectResponse project;

    private List<BudgetCategoryResponse> budgetCategories;
}
