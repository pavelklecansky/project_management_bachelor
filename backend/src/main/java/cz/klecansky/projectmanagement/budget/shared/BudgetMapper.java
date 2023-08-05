package cz.klecansky.projectmanagement.budget.shared;

import cz.klecansky.projectmanagement.budget.io.BudgetEntity;
import cz.klecansky.projectmanagement.budget.ui.response.BudgetResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BudgetMapper {

    @NonNull
    ModelMapper modelMapper;

    public BudgetEntity budgetCommandToBudgetEntity(BudgetCommand budgetCommand) {
        return modelMapper.map(budgetCommand, BudgetEntity.class);
    }

    public BudgetCommand budgetEntityToBudgetCommand(BudgetEntity budgetEntity) {
        return modelMapper.map(budgetEntity, BudgetCommand.class);
    }

    public BudgetResponse budgetCommandToBudgetResponse(BudgetCommand budgetCommand) {
        return modelMapper.map(budgetCommand, BudgetResponse.class);
    }
}
