package cz.klecansky.projectmanagement.budget.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class BudgetItemResponse {

    private UUID id;

    private String label;

    private double budget;
}
