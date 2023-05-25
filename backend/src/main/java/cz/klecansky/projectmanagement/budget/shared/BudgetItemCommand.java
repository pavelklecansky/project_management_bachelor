package cz.klecansky.projectmanagement.budget.shared;

import lombok.Data;

import java.util.UUID;

@Data
public class BudgetItemCommand {

    private UUID id;

    private String label;

    private double budget;

    private BudgetCategoryCommand budgetCategory;
}
