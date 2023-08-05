package cz.klecansky.projectmanagement.budget.shared;

import java.util.UUID;
import lombok.Data;

@Data
public class BudgetItemCommand {

    private UUID id;

    private String label;

    private double budget;

    private BudgetCategoryCommand budgetCategory;
}
