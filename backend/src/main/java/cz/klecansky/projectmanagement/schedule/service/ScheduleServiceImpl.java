package cz.klecansky.projectmanagement.schedule.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.schedule.io.*;
import cz.klecansky.projectmanagement.schedule.shared.*;
import java.util.Comparator;
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
public class ScheduleServiceImpl implements ScheduleService {

    @NonNull
    ScheduleRepository scheduleRepository;

    @NonNull
    ScheduleMapper scheduleMapper;

    @NonNull
    RowMapper rowMapper;

    @NonNull
    TaskScheduleMapper taskScheduleMapper;

    @NonNull
    TaskScheduleRepository taskScheduleRepository;

    @NonNull
    RowRepository rowRepository;

    @Override
    public Optional<ScheduleCommand> getByProject(UUID id) throws NoSuchElementFoundException {
        return scheduleRepository.findAll().stream()
                .filter(scheduleEntity -> scheduleEntity.getProject().getId().equals(id))
                .map(scheduleMapper::scheduleEntityToScheduleCommand)
                .findFirst();
    }

    @Override
    public ScheduleCommand createTask(UUID id, TaskCommand taskRequestToTaskCommand) {
        taskRequestToTaskCommand.setRealId(UUID.randomUUID());
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        Integer maxInt = scheduleEntity.getTasks().stream()
                .max(Comparator.comparing(TaskScheduleEntity::getId))
                .map(rowEntity -> rowEntity.getId())
                .orElse(0);
        taskRequestToTaskCommand.setId(maxInt + 1);
        TaskScheduleEntity taskScheduleEntity = taskScheduleMapper.taskCommandToTaskEntity(taskRequestToTaskCommand);
        taskScheduleEntity.setSchedule(scheduleEntity);
        taskScheduleEntity.setRow(scheduleEntity.getRows().stream()
                .filter(rowEntity -> rowEntity.getId() == taskScheduleEntity.getResourceId())
                .findFirst()
                .orElseThrow(NoSuchElementFoundException::new));
        scheduleEntity.getTasks().add(taskScheduleEntity);
        ScheduleEntity save = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.scheduleEntityToScheduleCommand(save);
    }

    @Override
    public ScheduleCommand createRow(UUID id, RowCommand rowRequestToRowCommand) {
        rowRequestToRowCommand.setRealId(UUID.randomUUID());
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        Integer maxInt = scheduleEntity.getRows().stream()
                .max(Comparator.comparing(RowEntity::getId))
                .map(rowEntity -> rowEntity.getId())
                .orElse(0);
        rowRequestToRowCommand.setId(maxInt + 1);
        RowEntity rowEntity = rowMapper.rowCommandToRowEntity(rowRequestToRowCommand);
        rowEntity.setSchedule(scheduleEntity);
        scheduleEntity.getRows().add(rowEntity);
        ScheduleEntity save = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.scheduleEntityToScheduleCommand(save);
    }

    @Override
    public void deleteTask(UUID id) {
        taskScheduleRepository.deleteById(id);
    }

    @Override
    public void deleteRow(UUID id) {
        rowRepository.deleteById(id);
    }

    @Override
    public Optional<TaskCommand> getTask(UUID id) {
        return taskScheduleRepository.findById(id).map(taskScheduleMapper::taskEntityToTaskCommand);
    }

    @Override
    public TaskCommand updateTask(UUID id, TaskCommand taskRequestToTaskCommand) {
        TaskScheduleEntity taskScheduleEntity =
                taskScheduleRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        taskScheduleEntity.setLabel(taskRequestToTaskCommand.getLabel());
        taskScheduleEntity.setFromDate(taskRequestToTaskCommand.getFromDate());
        taskScheduleEntity.setToDate(taskRequestToTaskCommand.getToDate());
        taskScheduleEntity.setResourceId(taskRequestToTaskCommand.getResourceId());
        return taskScheduleMapper.taskEntityToTaskCommand(taskScheduleRepository.save(taskScheduleEntity));
    }

    @Override
    public List<RowCommand> getRows(UUID id) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        return scheduleEntity.getRows().stream()
                .map(rowMapper::rowEntityToRowCommand)
                .toList();
    }

    @Override
    public RowCommand updateRow(UUID id, RowCommand rowRequestToRowCommand) {
        RowEntity rowEntity = rowRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        rowEntity.setLabel(rowRequestToRowCommand.getLabel());
        return rowMapper.rowEntityToRowCommand(rowRepository.save(rowEntity));
    }
}
