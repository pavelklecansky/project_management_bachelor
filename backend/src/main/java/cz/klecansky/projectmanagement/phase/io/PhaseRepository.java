package cz.klecansky.projectmanagement.phase.io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhaseRepository extends JpaRepository<PhaseEntity, UUID> {
}
