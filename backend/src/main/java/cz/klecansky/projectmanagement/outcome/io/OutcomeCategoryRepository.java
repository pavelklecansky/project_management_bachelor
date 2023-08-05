package cz.klecansky.projectmanagement.outcome.io;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeCategoryRepository extends JpaRepository<OutcomeCategoryEntity, UUID> {}
