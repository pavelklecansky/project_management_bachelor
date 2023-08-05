package cz.klecansky.projectmanagement.budget.shared;

import cz.klecansky.projectmanagement.budget.io.BudgetCategoryEntity;
import cz.klecansky.projectmanagement.budget.ui.request.BudgetCategoryRequest;
import cz.klecansky.projectmanagement.budget.ui.response.BudgetCategoryResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BudgetCategoryMapper {

    @NonNull
    ModelMapper modelMapper;

    public BudgetCategoryEntity budgetCategoryCommandToBudgetCategoryEntity(
            BudgetCategoryCommand budgetCategoryCommand) {
        return modelMapper.map(budgetCategoryCommand, BudgetCategoryEntity.class);
    }

    public BudgetCategoryCommand budgetCategoryEntityToBudgetCategoryCommand(
            BudgetCategoryEntity budgetCategoryEntity) {
        return modelMapper.map(budgetCategoryEntity, BudgetCategoryCommand.class);
    }

    public BudgetCategoryCommand budgetCategoryRequestToBudgetCategoryCommand(
            BudgetCategoryRequest budgetCategoryRequest) {
        return modelMapper.map(budgetCategoryRequest, BudgetCategoryCommand.class);
    }

    public BudgetCategoryResponse budgetCategoryCommandToBudgetCategoryResponse(
            BudgetCategoryCommand budgetCategoryCommand) {
        return modelMapper.map(budgetCategoryCommand, BudgetCategoryResponse.class);
    }
}
