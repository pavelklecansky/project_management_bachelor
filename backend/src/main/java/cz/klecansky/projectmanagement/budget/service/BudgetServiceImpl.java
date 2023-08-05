package cz.klecansky.projectmanagement.budget.service;

import cz.klecansky.projectmanagement.budget.io.*;
import cz.klecansky.projectmanagement.budget.shared.*;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    @NonNull
    BudgetRepository budgetRepository;

    @NonNull
    BudgetCategoryRepository budgetCategoryRepository;

    @NonNull
    BudgetItemRepository budgetItemRepository;

    @NonNull
    BudgetMapper budgetMapper;

    @NonNull
    BudgetCategoryMapper budgetCategoryMapper;

    @NonNull
    BudgetItemMapper budgetItemMapper;

    @Override
    public Optional<BudgetCommand> getByProject(UUID id) throws NoSuchElementFoundException {
        return budgetRepository.findAll().stream()
                .filter(budgetEntity -> budgetEntity.getProject().getId().equals(id))
                .map(budgetMapper::budgetEntityToBudgetCommand)
                .findFirst();
    }

    @Override
    public BudgetCategoryCommand createBudgetCategory(UUID id, BudgetCategoryCommand budgetCategoryCommand) {
        budgetCategoryCommand.setId(UUID.randomUUID());
        BudgetEntity budgetEntity = budgetRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        BudgetCategoryEntity budgetCategoryEntity =
                budgetCategoryMapper.budgetCategoryCommandToBudgetCategoryEntity(budgetCategoryCommand);
        budgetCategoryEntity.setBudgetEntity(budgetEntity);
        BudgetCategoryEntity save = budgetCategoryRepository.save(budgetCategoryEntity);
        return budgetCategoryMapper.budgetCategoryEntityToBudgetCategoryCommand(save);
    }

    @Override
    public BudgetItemCommand createBudgetItem(UUID id, BudgetItemCommand budgetItemCommand) {
        budgetItemCommand.setId(UUID.randomUUID());
        BudgetCategoryEntity budgetCategoryEntity =
                budgetCategoryRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        BudgetItemEntity budgetItemEntity = budgetItemMapper.budgetItemCommandToBudgetItemEntity(budgetItemCommand);
        budgetItemEntity.setBudgetCategory(budgetCategoryEntity);
        BudgetItemEntity save = budgetItemRepository.save(budgetItemEntity);
        return budgetItemMapper.budgetItemEntityToBudgetItemCommand(save);
    }

    @Override
    public void deleteCategory(UUID id) {
        budgetCategoryRepository.deleteById(id);
    }

    @Override
    public void deleteItem(UUID id) {
        budgetItemRepository.deleteById(id);
    }

    @Override
    public BudgetCategoryCommand updateCategory(UUID id, BudgetCategoryCommand budgetCategoryCommand) {
        BudgetCategoryEntity budgetCategoryEntity =
                budgetCategoryRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        budgetCategoryEntity.setLabel(budgetCategoryCommand.getLabel());
        budgetCategoryEntity.setBudget(budgetCategoryCommand.getBudget());
        return budgetCategoryMapper.budgetCategoryEntityToBudgetCategoryCommand(
                budgetCategoryRepository.save(budgetCategoryEntity));
    }

    @Override
    public BudgetItemCommand updateItem(UUID id, BudgetItemCommand budgetItemCommand) {
        BudgetItemEntity budgetItemEntity =
                budgetItemRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        budgetItemEntity.setLabel(budgetItemCommand.getLabel());
        budgetItemEntity.setBudget(budgetItemCommand.getBudget());
        return budgetItemMapper.budgetItemEntityToBudgetItemCommand(budgetItemRepository.save(budgetItemEntity));
    }

    @Override
    public Optional<BudgetCategoryCommand> getCategory(UUID id) {
        return budgetCategoryRepository
                .findById(id)
                .map(budgetCategoryMapper::budgetCategoryEntityToBudgetCategoryCommand);
    }

    @Override
    public Optional<BudgetItemCommand> getItem(UUID id) {
        return budgetItemRepository.findById(id).map(budgetItemMapper::budgetItemEntityToBudgetItemCommand);
    }

    @Override
    public Optional<BudgetCategoryCommand> getItemCategory(UUID id) {
        BudgetItemEntity budgetItemEntity =
                budgetItemRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        return Optional.of(
                budgetCategoryMapper.budgetCategoryEntityToBudgetCategoryCommand(budgetItemEntity.getBudgetCategory()));
    }
}
