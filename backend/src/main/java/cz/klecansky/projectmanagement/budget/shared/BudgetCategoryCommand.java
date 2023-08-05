package cz.klecansky.projectmanagement.budget.shared;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BudgetCategoryCommand {

    private UUID id;

    private String label;

    private double budget;

    private List<BudgetItemCommand> items;

    private BudgetCommand budgetEntity;
}
