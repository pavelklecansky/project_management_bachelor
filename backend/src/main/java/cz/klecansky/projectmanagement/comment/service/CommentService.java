package cz.klecansky.projectmanagement.comment.service;

import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.comment.shared.CommentCreationCommand;
import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.task.io.TaskRepository;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentService {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    TaskRepository taskRepository;

    @NonNull
    ByIdLoader<UserEntity, UUID> userLoader;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @NonNull
    TaskMapper taskMapper;

    public ProjectCommand addProjectComment(CommentCreationCommand command) {
        ProjectEntity projectEntity =
                projectRepository.findById(command.id()).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getComments().add(createProjectComment(command));
        return oldProjectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public ProjectCommand deleteProjectComment(UUID projectId, UUID commentId) {
        ProjectEntity projectEntity =
                projectRepository.findById(projectId).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getComments().removeIf(comment -> comment.getId().equals(commentId));
        return oldProjectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public TaskCommand addTaskComment(CommentCreationCommand command) {
        TaskEntity taskEntity = taskRepository.findById(command.id()).orElseThrow(NoSuchElementFoundException::new);
        taskEntity.getComments().add(createProjectComment(command));
        return taskMapper.taskEntityToTaskCommand(taskRepository.save(taskEntity));
    }

    public TaskCommand deleteTaskComment(UUID id, UUID idComment) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        taskEntity.getComments().removeIf(comment -> comment.getId().equals(idComment));
        return taskMapper.taskEntityToTaskCommand(taskRepository.save(taskEntity));
    }

    private CommentEntity createProjectComment(CommentCreationCommand command) {
        UserEntity user = userLoader.getById(command.userId());
        return new CommentEntity(UUID.randomUUID(), command.text(), Instant.now(), user);
    }
}
