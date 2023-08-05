package cz.klecansky.projectmanagement.budget.ui.response;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BudgetCategoryResponse {

    private UUID id;

    private String label;

    private double budget;

    private List<BudgetItemResponse> items;
}
