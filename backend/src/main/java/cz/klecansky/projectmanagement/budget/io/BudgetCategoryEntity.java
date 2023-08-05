package cz.klecansky.projectmanagement.budget.io;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "budget_categoryies")
@Getter
@Setter
public class BudgetCategoryEntity {

    @Id
    private UUID id;

    private String label;

    private double budget;

    @OneToMany(mappedBy = "budgetCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetItemEntity> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "budget_id")
    private BudgetEntity budgetEntity;

    @PreRemove
    private void preRemove() {
        items.clear();
    }
}
