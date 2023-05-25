package cz.klecansky.projectmanagement.task.service;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.comment.shared.CommentMapper;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.shared.mappers.GroupMapper;
import cz.klecansky.projectmanagement.phase.shared.PhaseMapper;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.task.io.TaskRepository;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import cz.klecansky.projectmanagement.user.service.EmailService;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @NonNull TaskRepository taskRepository;
    @NonNull TaskMapper taskMapper;
    @NonNull UserMapper userMapper;
    @NonNull GroupMapper groupMapper;
    @NonNull CommentMapper commentMapper;
    @NonNull PhaseMapper phaseMapper;
    @NonNull EmailService emailService;

    @Override
    public TaskCommand create(TaskCommand taskCommand) {
        taskCommand.setId(UUID.randomUUID());
        TaskEntity savedEntity = taskRepository.save(taskMapper.taskCommandToTaskEntity(taskCommand));
        TaskCommand saveCommand = taskMapper.taskEntityToTaskCommand(savedEntity);
        if (saveCommand.getAssigned() != null) {
            emailService.sendAssignToTaskEmail(taskCommand);
        }
        if (saveCommand.getAssignedForGroup() != null) {
            emailService.sendAssignToGroupTaskEmail(taskCommand);
        }
        return saveCommand;
    }

    @Override
    public List<TaskCommand> getAll() {
        return taskRepository.findAll().stream().map(taskMapper::taskEntityToTaskCommand).toList();
    }

    @Override
    public Optional<TaskCommand> get(UUID id) throws NoSuchElementFoundException {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        return taskEntity.map(taskMapper::taskEntityToTaskCommand);
    }

    @Override
    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskCommand update(UUID id, TaskCommand taskCommand) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        taskEntity.setDescription(taskCommand.getDescription());
        taskEntity.setName(taskCommand.getName());
        taskEntity.setStartDate(taskCommand.getStartDate());
        taskEntity.setEndDate(taskCommand.getEndDate());
        taskEntity.setPriority(taskCommand.getPriority());
        taskEntity.setStatus(taskCommand.getStatus());
        taskEntity.setProgress(taskCommand.getProgress());
        if (taskCommand.getAssigned() != null) {
            if (!taskEntity.getAssigned().getId().equals(taskCommand.getAssigned().getId())) {
                taskCommand.setId(taskEntity.getId());
                emailService.sendAssignToTaskEmail(taskCommand);
            }
            taskEntity.setAssigned(userMapper.userCommandToUserEntity(taskCommand.getAssigned()));

        } else {
            taskEntity.setAssigned(null);
        }
        if (taskCommand.getAssignedForGroup() != null) {
            if (Objects.isNull(taskEntity.getAssignedForGroup()) || !taskEntity.getAssignedForGroup().getId().equals(taskCommand.getAssignedForGroup().getId())) {
                taskCommand.setId(taskEntity.getId());
                emailService.sendAssignToGroupTaskEmail(taskCommand);
            }
            taskEntity.setAssignedForGroup(groupMapper.groupCommandToGroupEntity(taskCommand.getAssignedForGroup()));
        } else {
            taskEntity.setAssignedForGroup(null);
        }
        if (taskCommand.getPhase() != null) {
            taskEntity.setPhase(phaseMapper.phaseCommandToPhaseEntity(taskCommand.getPhase()));
        } else {
            taskEntity.setPhase(null);
        }
        return taskMapper.taskEntityToTaskCommand(taskRepository.save(taskEntity));
    }

    @Override
    public TaskCommand deleteComment(UUID id, UUID idComment) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        taskEntity.getComments().removeIf(comment -> comment.getId().equals(idComment));
        return taskMapper.taskEntityToTaskCommand(taskRepository.save(taskEntity));
    }

    @Override
    public TaskCommand addComment(UUID id, CommentCommand commentCommand) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        taskEntity.getComments().add(commentMapper.commentCommandToCommentEntity(commentCommand));
        return taskMapper.taskEntityToTaskCommand(taskRepository.save(taskEntity));
    }
}
