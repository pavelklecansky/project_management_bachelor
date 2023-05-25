package cz.klecansky.projectmanagement.project.io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    Optional<ProjectEntity> findByPhases_Id(UUID id);
    Optional<ProjectEntity> findByTasks_Id(UUID id);
}
