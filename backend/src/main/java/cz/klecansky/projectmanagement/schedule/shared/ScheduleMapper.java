package cz.klecansky.projectmanagement.schedule.shared;

import cz.klecansky.projectmanagement.schedule.io.ScheduleEntity;
import cz.klecansky.projectmanagement.schedule.ui.response.ScheduleResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduleMapper {

    @NonNull
    ModelMapper modelMapper;

    public ScheduleEntity scheduleCommandToScheduleEntity(ScheduleCommand scheduleCommand) {
        return modelMapper.map(scheduleCommand, ScheduleEntity.class);
    }

    public ScheduleCommand scheduleEntityToScheduleCommand(ScheduleEntity scheduleEntity) {
        return modelMapper.map(scheduleEntity, ScheduleCommand.class);
    }

    public ScheduleResponse scheduleCommandToScheduleResponse(ScheduleCommand scheduleCommand) {
        return modelMapper.map(scheduleCommand, ScheduleResponse.class);
    }
}
