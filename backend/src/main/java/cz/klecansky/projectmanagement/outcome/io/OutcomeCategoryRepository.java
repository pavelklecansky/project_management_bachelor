package cz.klecansky.projectmanagement.outcome.io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutcomeCategoryRepository extends JpaRepository<OutcomeCategoryEntity, UUID> {
}
