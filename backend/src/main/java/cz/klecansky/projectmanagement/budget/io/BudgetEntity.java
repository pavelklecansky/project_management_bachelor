package cz.klecansky.projectmanagement.budget.io;

import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class BudgetEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @OneToMany(mappedBy = "budgetEntity", cascade = CascadeType.ALL)
    private List<BudgetCategoryEntity> budgetCategories = new ArrayList<>();
}
