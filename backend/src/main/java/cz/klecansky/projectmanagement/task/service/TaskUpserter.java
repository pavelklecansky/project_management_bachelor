package cz.klecansky.projectmanagement.task.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.io.GroupLoader;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.phase.io.PhaseLoader;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectLoader;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.task.io.TaskLoader;
import cz.klecansky.projectmanagement.task.io.TaskRepository;
import cz.klecansky.projectmanagement.task.shared.TaskUpsertCommand;
import cz.klecansky.projectmanagement.task.shared.UserAssignToTaskCommand;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.repository.UserLoader;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskUpserter {

    @NonNull
    TaskLoader taskLoader;

    @NonNull
    TaskRepository taskRepository;

    @NonNull
    ProjectLoader projectLoader;

    @NonNull
    UserLoader userLoader;

    @NonNull
    GroupLoader groupLoader;

    @NonNull
    PhaseLoader phaseLoader;

    @NonNull
    TaskNotifier notifier;

    @Transactional
    @NonNull
    public TaskEntity upsert(@NonNull TaskUpsertCommand command) {
        TaskEntity taskEntity = taskLoader
                .findById(command.id())
                .map(existingProject -> updateExistingTask(existingProject, command))
                .orElseGet(() -> createNewTask(command));
        return taskRepository.save(taskEntity);
    }

    private TaskEntity createNewTask(TaskUpsertCommand command) {
        ProjectEntity project = projectLoader.getById(command.project());
        validateTask(command.startDate(), project);

        UserEntity assigned = command.assigned() == null ? null : userLoader.getById(command.assigned());
        GroupEntity assignedGroup =
                command.assignedForGroup() == null ? null : groupLoader.getById(command.assignedForGroup());
        PhaseEntity phase = command.assignedForGroup() == null ? null : phaseLoader.getById(command.assignedForGroup());

        if (assigned != null) {
            notifier.notifyUserAssignedToTask(command.id(), command.name(), assigned.getEmail());
        }

        if (assignedGroup != null) {
            notifier.notifyGroupAssignedToTask(assignedGroup.getMembers().stream()
                    .map(groupMemberEntity -> new UserAssignToTaskCommand(
                            command.id(),
                            command.name(),
                            groupMemberEntity.getUser().getEmail()))
                    .toList());
        }

        return new TaskEntity(
                command.id(),
                command.name(),
                command.description(),
                command.startDate(),
                command.endDate(),
                command.priority(),
                command.status(),
                command.progress(),
                assigned,
                assignedGroup,
                phase,
                project,
                List.of());
    }

    private void validateTask(Instant startDate, ProjectEntity project) {
        if (startDate.isBefore(project.getStartDate())) {
            throw new NoSuchElementFoundException("Task cannot start before project start date");
        }
    }

    private TaskEntity updateExistingTask(TaskEntity existingTask, TaskUpsertCommand command) {

        if (command.name() != null) {
            existingTask = existingTask.withDescription(command.name());
        }
        if (command.description() != null) {
            existingTask = existingTask.withDescription(command.description());
        }
        if (command.startDate() != null) {
            existingTask = existingTask.withStartDate(command.startDate());
        }
        if (command.endDate() != null) {
            existingTask = existingTask.withEndDate(command.endDate());
        }
        existingTask = existingTask.withPriority(command.priority());
        if (command.status() != null) {
            existingTask = existingTask.withStatus(command.status());
        }
        existingTask = existingTask.withProgress(command.progress());

        if (command.assigned() != null) {
            if (!existingTask.getAssigned().getId().equals(command.assigned())) {
                notifier.notifyUserAssignedToTask(
                        existingTask.getId(),
                        existingTask.getName(),
                        existingTask.getAssigned().getEmail());
            }
            ProjectEntity projectEntity = projectLoader.getById(command.project());
            validateTask(existingTask.getStartDate(), projectEntity);
            existingTask = existingTask.withProject(projectEntity);
        }
        if (command.assignedForGroup() != null) {
            if (Objects.isNull(existingTask.getAssignedForGroup())
                    || !existingTask.getAssignedForGroup().getId().equals(command.assignedForGroup())) {
                notifier.notifyGroupAssignedToTask(existingTask.getAssignedForGroup().getMembers().stream()
                        .map(groupMemberEntity -> new UserAssignToTaskCommand(
                                command.id(),
                                command.name(),
                                groupMemberEntity.getUser().getEmail()))
                        .toList());
            }
            existingTask = existingTask.withAssignedForGroup(groupLoader.getById(command.assignedForGroup()));
        }

        if (command.phase() != null) {
            existingTask = existingTask.withPhase(phaseLoader.getById(command.phase()));
        }
        return existingTask;
    }
}
