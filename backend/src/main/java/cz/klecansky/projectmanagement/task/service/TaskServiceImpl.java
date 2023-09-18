package cz.klecansky.projectmanagement.task.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.task.io.TaskRepository;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import cz.klecansky.projectmanagement.task.shared.TaskUpsertCommand;
import cz.klecansky.projectmanagement.task.utils.TaskUtils;
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
public class TaskServiceImpl implements TaskService {

    @NonNull
    TaskRepository taskRepository;

    @NonNull
    TaskMapper taskMapper;

    @NonNull
    TaskUpserter taskUpserter;

    @Override
    public TaskCommand create(TaskUpsertCommand taskCommand) {
        TaskEntity savedEntity = taskUpserter.upsert(taskCommand);
        return taskMapper.taskEntityToTaskCommand(savedEntity);
    }

    @Override
    public List<TaskCommand> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::taskEntityToTaskCommand)
                .toList();
    }

    @Override
    public List<TaskCommand> getUsersTasks(UUID userId) {
        return getAll().stream()
                .filter(taskCommand -> TaskUtils.isAssignedToTask(taskCommand, userId))
                .toList();
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
    public TaskCommand update(TaskUpsertCommand taskCommand) {
        TaskEntity taskEntity = taskUpserter.upsert(taskCommand);
        return taskMapper.taskEntityToTaskCommand(taskEntity);
    }
}
