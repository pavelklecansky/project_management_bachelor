package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {

    ProjectCommand create(ProjectCommand projectCommand);

    List<ProjectCommand> getAll();

    Optional<ProjectCommand> get(UUID id) throws NoSuchElementFoundException;

    Optional<ProjectCommand> getByPhaseId(UUID id) throws NoSuchElementFoundException;

    Optional<ProjectCommand> getByTasksId(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    ProjectCommand update(UUID id, ProjectCommand projectCommand);
}
