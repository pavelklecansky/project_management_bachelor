package cz.klecansky.projectmanagement.budget.service;

import cz.klecansky.projectmanagement.budget.shared.BudgetCategoryCommand;
import cz.klecansky.projectmanagement.budget.shared.BudgetCommand;
import cz.klecansky.projectmanagement.budget.shared.BudgetItemCommand;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import java.util.Optional;
import java.util.UUID;

public interface BudgetService {
    Optional<BudgetCommand> getByProject(UUID id) throws NoSuchElementFoundException;

    BudgetCategoryCommand createBudgetCategory(
            UUID id, BudgetCategoryCommand budgetCategoryRequestToBudgetCategoryCommand);

    BudgetItemCommand createBudgetItem(UUID id, BudgetItemCommand budgetItemRequestToBudgetItemCommand);

    void deleteCategory(UUID id);

    void deleteItem(UUID id);

    BudgetCategoryCommand updateCategory(UUID id, BudgetCategoryCommand budgetCategoryRequestToBudgetCategoryCommand);

    BudgetItemCommand updateItem(UUID id, BudgetItemCommand budgetItemRequestToBudgetItemCommand);

    Optional<BudgetCategoryCommand> getCategory(UUID id);

    Optional<BudgetItemCommand> getItem(UUID id);

    Optional<BudgetCategoryCommand> getItemCategory(UUID id);
}
