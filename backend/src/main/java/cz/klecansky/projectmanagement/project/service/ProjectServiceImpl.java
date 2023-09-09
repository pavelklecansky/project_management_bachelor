package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectUpsertCommand;
import cz.klecansky.projectmanagement.projectmember.utils.MemberUtils;
import cz.klecansky.projectmanagement.security.SecurityUtils;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.storage.service.StorageService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @NonNull
    ProjectUpserter projectUpserter;

    @NonNull
    StorageService storageService;

    @Override
    public ProjectCommand create(ProjectUpsertCommand projectCommand) {
        ProjectCommand upsert = oldProjectMapper.projectEntityToProjectCommand(projectUpserter.upsert(projectCommand));
        // TODO create proper handler for project creation
        storageService.createDictionaryInRoot(upsert.getId());
        return upsert;
    }

    @Override
    public List<ProjectCommand> getAll() {
        return projectRepository.findAll().stream()
                .map(oldProjectMapper::projectEntityToProjectCommand)
                .toList();
    }

    @Override
    public Optional<ProjectCommand> get(UUID id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        return projectEntity.map(oldProjectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByPhaseId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByPhasesId(id);
        return projectEntity.map(oldProjectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByTasksId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByTasksId(id);
        return projectEntity.map(oldProjectMapper::projectEntityToProjectCommand);
    }

    @Override
    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectCommand update(ProjectUpsertCommand upsertCommand) {
        ProjectEntity projectEntity = projectUpserter.upsert(upsertCommand);
        return oldProjectMapper.projectEntityToProjectCommand(projectEntity);
    }

    @Override
    public List<ProjectCommand> getUsersProjects(UserPrincipal userPrincipal) {
        return getAll().stream()
                .filter(projectCommand -> SecurityUtils.isAdmin(userPrincipal)
                        || MemberUtils.memberOfProject(projectCommand, userPrincipal))
                .toList();
    }
}
