package cz.klecansky.projectmanagement.budget.ui;

import cz.klecansky.projectmanagement.budget.service.BudgetService;
import cz.klecansky.projectmanagement.budget.shared.*;
import cz.klecansky.projectmanagement.budget.ui.request.BudgetCategoryRequest;
import cz.klecansky.projectmanagement.budget.ui.request.BudgetItemRequest;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static cz.klecansky.projectmanagement.core.WebConstants.BUDGET_API;

@RestController
@RequestMapping(BUDGET_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BudgetController {

    @NonNull BudgetService budgetService;
    @NonNull BudgetMapper budgetMapper;
    @NonNull BudgetCategoryMapper budgetCategoryMapper;
    @NonNull BudgetItemMapper budgetItemMapper;


    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBudgetByProject(@PathVariable UUID id) throws NoSuchElementFoundException {
        return budgetService.getByProject(id)
                .map(budgetCommand -> ResponseEntity.ok(budgetMapper.budgetCommandToBudgetResponse(budgetCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCategory(@PathVariable UUID id) throws NoSuchElementFoundException {
        return budgetService.getCategory(id)
                .map(category -> ResponseEntity.ok(budgetCategoryMapper.budgetCategoryCommandToBudgetCategoryResponse(category)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "item/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getItem(@PathVariable UUID id) throws NoSuchElementFoundException {
        return budgetService.getItem(id)
                .map(item -> ResponseEntity.ok(budgetItemMapper.budgetItemCommandToBudgetItemResponse(item)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "itemCategory/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getItemCategory(@PathVariable UUID id) throws NoSuchElementFoundException {
        return budgetService.getItemCategory(id)
                .map(category -> ResponseEntity.ok(budgetCategoryMapper.budgetCategoryCommandToBudgetCategoryResponse(category)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @PostMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createBudgetCategory(@PathVariable UUID id, @RequestBody BudgetCategoryRequest request) {
        BudgetCategoryCommand createBudgetCategory = budgetService.createBudgetCategory(id, budgetCategoryMapper.budgetCategoryRequestToBudgetCategoryCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder().message("Budget category was successfully created.").data(budgetCategoryMapper.budgetCategoryCommandToBudgetCategoryResponse(
                createBudgetCategory)).build());
    }

    @PostMapping(path = "item/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createBudgetItem(@PathVariable UUID id, @RequestBody BudgetItemRequest request) {
        BudgetItemCommand createdBudgetItem = budgetService.createBudgetItem(id, budgetItemMapper.budgetItemRequestToBudgetItemCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder().message("Budget item was successfully created.").data(budgetItemMapper.budgetItemCommandToBudgetItemResponse(
                createdBudgetItem)).build());
    }

    @PutMapping(path = "item/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateItem(@PathVariable UUID id, @RequestBody BudgetItemRequest request) {
        BudgetItemCommand budgetItemCommand = budgetService.updateItem(id, budgetItemMapper.budgetItemRequestToBudgetItemCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder().message("Item was successfully updated.").data(budgetItemMapper.budgetItemCommandToBudgetItemResponse(budgetItemCommand)).build());
    }

    @PutMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateCategory(@PathVariable UUID id, @RequestBody BudgetCategoryRequest request) {
        BudgetCategoryCommand budgetCategoryCommand = budgetService.updateCategory(id, budgetCategoryMapper.budgetCategoryRequestToBudgetCategoryCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder().message("Category was successfully updated.").data(budgetCategoryMapper.budgetCategoryCommandToBudgetCategoryResponse(budgetCategoryCommand)).build());
    }

    @DeleteMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable UUID id) {
        budgetService.deleteCategory(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Category was successfully deleted.").build());
    }

    @DeleteMapping(path = "item/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteItem(@PathVariable UUID id) {
        budgetService.deleteItem(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Item was successfully deleted.").build());
    }
}
