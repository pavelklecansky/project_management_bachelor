package cz.klecansky.projectmanagement.comment.service;

import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.comment.shared.CommentCreationCommand;
import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
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
    ByIdLoader<UUID, UserEntity> userLoader;

    @NonNull
    OldProjectMapper oldProjectMapper;

    public ProjectCommand addComment(CommentCreationCommand command) {
        ProjectEntity projectEntity =
                projectRepository.findById(command.projectId()).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getComments().add(createComment(command));
        return oldProjectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public ProjectCommand deleteComment(UUID projectId, UUID commentId) {
        ProjectEntity projectEntity =
                projectRepository.findById(projectId).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getComments().removeIf(comment -> comment.getId().equals(commentId));
        return oldProjectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    private CommentEntity createComment(CommentCreationCommand command) {
        UserEntity user = userLoader.getById(command.userId());
        return new CommentEntity(UUID.randomUUID(), command.text(), Instant.now(), user);
    }
}
