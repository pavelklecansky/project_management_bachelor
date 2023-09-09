package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.budget.io.BudgetEntity;
import cz.klecansky.projectmanagement.budget.io.BudgetRepository;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectLoader;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.ProjectUpsertCommand;
import cz.klecansky.projectmanagement.schedule.io.ScheduleEntity;
import cz.klecansky.projectmanagement.schedule.io.ScheduleRepository;
import cz.klecansky.projectmanagement.security.SecurityUtils;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectUpserter {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    ProjectLoader projectLoader;

    @NonNull
    BudgetRepository budgetRepository;

    @NonNull
    ScheduleRepository scheduleRepository;

    @Transactional
    @NonNull
    public ProjectEntity upsert(@NonNull ProjectUpsertCommand command) {
        return projectLoader
                .findById(command.id())
                .map(existingProject -> updateExistingProject(existingProject, command))
                .orElseGet(() -> createNewProject(command));
    }

    private ProjectEntity createNewProject(ProjectUpsertCommand command) {
        ProjectEntity projectEntity = createAndSaveProject(command);
        createAndSaveScheduleForProject(projectEntity);
        createAndSaveBudgetForProject(projectEntity);
        return projectEntity;
    }

    private ProjectEntity updateExistingProject(ProjectEntity existingProject, ProjectUpsertCommand command) {
        if (command.name() != null) {
            existingProject = existingProject.withName(command.name());
        }
        if (command.description() != null) {
            existingProject = existingProject.withDescription(command.description());
        }
        if (command.startDate() != null) {
            existingProject = existingProject.withStartDate(command.startDate());
        }
        if (command.endDate() != null) {
            existingProject = existingProject.withEndDate(command.endDate());
        }

        return projectRepository.save(existingProject);
    }

    private void createAndSaveBudgetForProject(ProjectEntity projectEntity) {
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(UUID.randomUUID());
        budgetEntity.setProject(projectEntity);
        budgetRepository.save(budgetEntity);
    }

    private void createAndSaveScheduleForProject(ProjectEntity projectEntity) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(UUID.randomUUID());
        scheduleEntity.setProject(projectEntity);
        scheduleRepository.save(scheduleEntity);
    }

    private ProjectEntity createAndSaveProject(ProjectUpsertCommand command) {
        ProjectEntity projectEntity = new ProjectEntity(
                command.id(),
                command.name(),
                command.description(),
                command.startDate(),
                command.endDate(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(SecurityUtils.getCurrentUser().getUser()),
                List.of(),
                List.of(),
                List.of());
        return projectRepository.save(projectEntity);
    }
}
