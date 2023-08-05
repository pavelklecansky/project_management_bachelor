package cz.klecansky.projectmanagement.schedule.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.SCHEDULES_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.schedule.service.ScheduleService;
import cz.klecansky.projectmanagement.schedule.shared.*;
import cz.klecansky.projectmanagement.schedule.ui.request.RowRequest;
import cz.klecansky.projectmanagement.schedule.ui.request.TaskRequest;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SCHEDULES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduleController {

    @NonNull
    ScheduleService scheduleService;

    @NonNull
    ScheduleMapper scheduleMapper;

    @NonNull
    TaskScheduleMapper taskMapper;

    @NonNull
    RowMapper rowMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getScheduleByProject(@PathVariable UUID id) throws NoSuchElementFoundException {
        return scheduleService
                .getByProject(id)
                .map(scheduleCommand ->
                        ResponseEntity.ok(scheduleMapper.scheduleCommandToScheduleResponse(scheduleCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "task/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getTask(@PathVariable UUID id) throws NoSuchElementFoundException {
        return scheduleService
                .getTask(id)
                .map(taskCommand -> ResponseEntity.ok(taskMapper.taskCommandToTaskResponse(taskCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "rows/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getRows(@PathVariable UUID id) throws NoSuchElementFoundException {
        return ResponseEntity.ok(scheduleService.getRows(id).stream()
                .map(rowMapper::rowCommandToRowResponse)
                .toList());
    }

    @PostMapping(path = "task/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        ScheduleCommand createTask = scheduleService.createTask(id, taskMapper.taskRequestToTaskCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully created.")
                .data(scheduleMapper.scheduleCommandToScheduleResponse(createTask))
                .build());
    }

    @PostMapping(path = "row/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createRow(@PathVariable UUID id, @RequestBody RowRequest request) {
        ScheduleCommand createRow = scheduleService.createRow(id, rowMapper.rowRequestToRowCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Row was successfully created.")
                .data(scheduleMapper.scheduleCommandToScheduleResponse(createRow))
                .build());
    }

    @PutMapping(path = "task/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        TaskCommand updatedTask = scheduleService.updateTask(id, taskMapper.taskRequestToTaskCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully updated.")
                .data(taskMapper.taskCommandToTaskResponse(updatedTask))
                .build());
    }

    @PutMapping(path = "row/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateRow(@PathVariable UUID id, @RequestBody RowRequest request) {
        RowCommand updateRow = scheduleService.updateRow(id, rowMapper.rowRequestToRowCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Row was successfully updated.")
                .data(rowMapper.rowCommandToRowResponse(updateRow))
                .build());
    }

    @DeleteMapping(path = "task/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteTask(@PathVariable UUID id) {
        scheduleService.deleteTask(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully deleted.")
                .build());
    }

    @DeleteMapping(path = "row/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteRow(@PathVariable UUID id) {
        scheduleService.deleteRow(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Row was successfully deleted.")
                .build());
    }
}
