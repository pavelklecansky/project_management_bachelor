package cz.klecansky.projectmanagement.budget.ui.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BudgetItemResponse {

    private UUID id;

    private String label;

    private double budget;
}
