package cz.klecansky.projectmanagement.phase.io;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<PhaseEntity, UUID> {}
