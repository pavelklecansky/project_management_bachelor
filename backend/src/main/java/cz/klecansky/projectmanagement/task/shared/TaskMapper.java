package cz.klecansky.projectmanagement.task.shared;

import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.task.ui.response.TaskResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Deprecated
public class TaskMapper {

    @NonNull
    ModelMapper modelMapper;

    public TaskCommand taskEntityToTaskCommand(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskCommand.class);
    }

    public TaskResponse taskCommandToTaskResponse(TaskCommand taskCommand) {
        return modelMapper.map(taskCommand, TaskResponse.class);
    }
}
