package cz.klecansky.projectmanagement.task.service;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    TaskCommand create(TaskCommand taskCommand);

    List<TaskCommand> getAll();

    Optional<TaskCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    TaskCommand update(UUID id, TaskCommand taskCommand);

    TaskCommand deleteComment(UUID id, UUID idComment);

    TaskCommand addComment(UUID id, CommentCommand commentCommand);
}
