package cz.klecansky.projectmanagement.budget.io;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategoryEntity, UUID> {}
