package cz.klecansky.projectmanagement.budget.shared;

import cz.klecansky.projectmanagement.budget.io.BudgetItemEntity;
import cz.klecansky.projectmanagement.budget.ui.request.BudgetItemRequest;
import cz.klecansky.projectmanagement.budget.ui.response.BudgetItemResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BudgetItemMapper {

    @NonNull
    ModelMapper modelMapper;

    public BudgetItemEntity budgetItemCommandToBudgetItemEntity(BudgetItemCommand budgetItemCommand) {
        return modelMapper.map(budgetItemCommand, BudgetItemEntity.class);
    }

    public BudgetItemCommand budgetItemEntityToBudgetItemCommand(BudgetItemEntity budgetItemScheduleEntity) {
        return modelMapper.map(budgetItemScheduleEntity, BudgetItemCommand.class);
    }

    public BudgetItemCommand budgetItemRequestToBudgetItemCommand(BudgetItemRequest budgetItemRequest) {
        return modelMapper.map(budgetItemRequest, BudgetItemCommand.class);
    }

    public BudgetItemResponse budgetItemCommandToBudgetItemResponse(BudgetItemCommand budgetItemCommand) {
        return modelMapper.map(budgetItemCommand, BudgetItemResponse.class);
    }
}
