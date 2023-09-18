package cz.klecansky.projectmanagement.task.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskUpsertCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    TaskCommand create(TaskUpsertCommand taskCommand);

    List<TaskCommand> getAll();

    List<TaskCommand> getUsersTasks(UUID userId);

    Optional<TaskCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    TaskCommand update(TaskUpsertCommand taskCommand);
}
