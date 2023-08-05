package cz.klecansky.projectmanagement.schedule.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.schedule.shared.RowCommand;
import cz.klecansky.projectmanagement.schedule.shared.ScheduleCommand;
import cz.klecansky.projectmanagement.schedule.shared.TaskCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleService {
    Optional<ScheduleCommand> getByProject(UUID id) throws NoSuchElementFoundException;

    ScheduleCommand createTask(UUID id, TaskCommand taskRequestToTaskCommand);

    ScheduleCommand createRow(UUID id, RowCommand rowRequestToRowCommand);

    void deleteTask(UUID id);

    void deleteRow(UUID id);

    Optional<TaskCommand> getTask(UUID id);

    TaskCommand updateTask(UUID id, TaskCommand taskRequestToTaskCommand);

    List<RowCommand> getRows(UUID id);

    RowCommand updateRow(UUID id, RowCommand rowRequestToRowCommand);
}
