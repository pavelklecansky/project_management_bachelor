package cz.klecansky.projectmanagement.budget.io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategoryEntity, UUID> {
}
