package cz.klecansky.projectmanagement.schedule.io;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends JpaRepository<RowEntity, UUID> {}
