package cz.klecansky.projectmanagement.budget.io;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "budget_items")
@Getter
@Setter
public class BudgetItemEntity {

    @Id
    private UUID id;

    private String label;

    private double budget;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "budget_category_id")
    private BudgetCategoryEntity budgetCategory;
}
