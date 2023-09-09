package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectUpsertCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {

    ProjectCommand create(ProjectUpsertCommand projectCommand);

    List<ProjectCommand> getAll();

    Optional<ProjectCommand> get(UUID id);

    Optional<ProjectCommand> getByPhaseId(UUID id);

    Optional<ProjectCommand> getByTasksId(UUID id);

    void delete(UUID id);

    ProjectCommand update(ProjectUpsertCommand upsertCommand);

    List<ProjectCommand> getUsersProjects(UserPrincipal userPrincipal);
}
