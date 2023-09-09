package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.budget.io.BudgetEntity;
import cz.klecansky.projectmanagement.budget.io.BudgetRepository;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
import cz.klecansky.projectmanagement.schedule.io.ScheduleEntity;
import cz.klecansky.projectmanagement.schedule.io.ScheduleRepository;
import cz.klecansky.projectmanagement.security.SecurityUtils;
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
    ProjectMapper projectMapper;

    @NonNull
    ScheduleRepository scheduleRepository;

    @NonNull
    BudgetRepository budgetRepository;

    @Override
    public ProjectCommand create(ProjectCommand projectCommand) {
        projectCommand.setId(UUID.randomUUID());
        ProjectEntity projectEntity = projectMapper.projectCommandToProjectEntity(projectCommand);
        projectEntity.setMembers(List.of(SecurityUtils.getCurrentUser().getUser()));
        ProjectEntity savedEntity = projectRepository.save(projectEntity);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(UUID.randomUUID());
        scheduleEntity.setProject(projectEntity);
        scheduleRepository.save(scheduleEntity);
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(UUID.randomUUID());
        budgetEntity.setProject(projectEntity);
        budgetRepository.save(budgetEntity);
        return projectMapper.projectEntityToProjectCommand(savedEntity);
    }

    @Override
    public List<ProjectCommand> getAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::projectEntityToProjectCommand)
                .toList();
    }

    @Override
    public Optional<ProjectCommand> get(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByPhaseId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByPhases_Id(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByTasksId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByTasks_Id(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectCommand update(UUID id, ProjectCommand projectCommand) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.setDescription(projectCommand.getDescription());
        projectEntity.setName(projectCommand.getName());
        projectEntity.setStartDate(projectCommand.getStartDate());
        projectEntity.setEndDate(projectCommand.getEndDate());
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }
}
