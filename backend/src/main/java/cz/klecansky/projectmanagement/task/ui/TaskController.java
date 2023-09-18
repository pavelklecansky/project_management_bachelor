package cz.klecansky.projectmanagement.task.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.TASKS_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.task.service.TaskService;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import cz.klecansky.projectmanagement.task.shared.TaskUpsertCommand;
import cz.klecansky.projectmanagement.task.ui.request.TaskRequest;
import cz.klecansky.projectmanagement.task.ui.response.TaskResponse;
import io.jsonwebtoken.lang.Assert;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TASKS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskController {

    @NonNull
    TaskService taskService;

    @NonNull
    TaskMapper taskMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public TaskResponse getTask(@PathVariable UUID id) throws NoSuchElementFoundException {
        return taskService
                .get(id)
                .map(taskMapper::taskCommandToTaskResponse)
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<TaskResponse> getTasks() {
        return taskService.getAll().stream()
                .map(taskMapper::taskCommandToTaskResponse)
                .toList();
    }

    @GetMapping("/usersTasks")
    @PreAuthorize("isAuthenticated()")
    public List<TaskResponse> getUsersTasks(
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        return taskService.getUsersTasks(userPrincipal.getUser().getId()).stream()
                .map(taskMapper::taskCommandToTaskResponse)
                .toList();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> createTask(@RequestBody TaskRequest request) {
        TaskCommand createdProject = taskService.create(createUpsertCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was created successfully.")
                .data(taskMapper.taskCommandToTaskResponse(createdProject))
                .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> updateTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        Assert.state(id.equals(request.getId()), "Path variable and body ids of task should equal.");
        TaskCommand update = taskService.update(createUpsertCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully updated.")
                .data(taskMapper.taskCommandToTaskResponse(update))
                .build());
    }

    private TaskUpsertCommand createUpsertCommand(TaskRequest request) {
        UUID id = request.getId() == null ? UUID.randomUUID() : request.getId();
        return new TaskUpsertCommand(
                id,
                request.getName(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getPriority(),
                request.getStatus(),
                request.getProgress(),
                request.getProject(),
                request.getAssigned(),
                request.getAssignedForGroup(),
                request.getPhase());
    }
}
