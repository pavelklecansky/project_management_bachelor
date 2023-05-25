package cz.klecansky.projectmanagement.budget.ui.response;


import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BudgetResponse {

    private UUID id;

    private ProjectResponse project;

    private List<BudgetCategoryResponse> budgetCategories;
}
