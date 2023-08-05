package cz.klecansky.projectmanagement.schedule.shared;

import cz.klecansky.projectmanagement.schedule.io.TaskScheduleEntity;
import cz.klecansky.projectmanagement.schedule.ui.request.TaskRequest;
import cz.klecansky.projectmanagement.schedule.ui.response.TaskResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskScheduleMapper {

    @NonNull
    ModelMapper modelMapper;

    public TaskScheduleEntity taskCommandToTaskEntity(TaskCommand taskCommand) {
        return modelMapper.map(taskCommand, TaskScheduleEntity.class);
    }

    public TaskCommand taskEntityToTaskCommand(TaskScheduleEntity taskScheduleEntity) {
        return modelMapper.map(taskScheduleEntity, TaskCommand.class);
    }

    public TaskCommand taskRequestToTaskCommand(TaskRequest taskRequest) {
        return modelMapper.map(taskRequest, TaskCommand.class);
    }

    public TaskResponse taskCommandToTaskResponse(TaskCommand taskCommand) {
        return modelMapper.map(taskCommand, TaskResponse.class);
    }
}
