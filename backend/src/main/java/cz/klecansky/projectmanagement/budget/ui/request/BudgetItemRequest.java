package cz.klecansky.projectmanagement.budget.ui.request;

import lombok.Data;

@Data
public class BudgetItemRequest {

    private String label;

    private double budget;
}
