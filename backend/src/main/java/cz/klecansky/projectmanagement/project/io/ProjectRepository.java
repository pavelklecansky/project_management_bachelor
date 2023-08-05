package cz.klecansky.projectmanagement.project.io;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    Optional<ProjectEntity> findByPhases_Id(UUID id);

    Optional<ProjectEntity> findByTasks_Id(UUID id);
}
