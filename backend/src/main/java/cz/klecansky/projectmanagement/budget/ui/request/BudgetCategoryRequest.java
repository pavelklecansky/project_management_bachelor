package cz.klecansky.projectmanagement.budget.ui.request;

import lombok.Data;

@Data
public class BudgetCategoryRequest {
    private String label;

    private double budget;
}
