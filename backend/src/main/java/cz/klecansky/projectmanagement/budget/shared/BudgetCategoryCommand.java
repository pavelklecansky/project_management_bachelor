package cz.klecansky.projectmanagement.budget.shared;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BudgetCategoryCommand {

    private UUID id;

    private String label;

    private double budget;

    private List<BudgetItemCommand> items;

    private BudgetCommand budgetEntity;
}
