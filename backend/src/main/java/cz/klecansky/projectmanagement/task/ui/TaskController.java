package cz.klecansky.projectmanagement.task.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.TASKS_API;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.comment.shared.CommentMapper;
import cz.klecansky.projectmanagement.comment.ui.request.CommentRequest;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.task.service.TaskService;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import cz.klecansky.projectmanagement.task.ui.request.TaskRequest;
import cz.klecansky.projectmanagement.task.ui.response.TaskResponse;
import cz.klecansky.projectmanagement.task.utils.TaskUtils;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(TASKS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskController {

    @NonNull
    TaskService taskService;

    @NonNull
    TaskMapper taskMapper;

    @NonNull
    ProjectService projectService;

    @NonNull
    Converter<UUID, ProjectCommand> UUIDToProjectCommandConverter;

    @NonNull
    Converter<UUID, UserCommand> UUIDToUserCommandConverter;

    @NonNull
    Converter<UUID, GroupCommand> UUIDToGroupCommandConverter;

    @NonNull
    Converter<UUID, PhaseCommand> UUIDToPhaseCommandConverter;

    @NonNull
    CommentMapper commentMapper;

    @NonNull
    UserService userService;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getTask(@PathVariable UUID id) throws NoSuchElementFoundException {
        return taskService
                .get(id)
                .map(taskCommand -> ResponseEntity.ok(taskMapper.taskCommandToTaskResponse(taskCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getTasks() {
        List<TaskResponse> taskResponse = taskService.getAll().stream()
                .map(taskMapper::taskCommandToTaskResponse)
                .toList();
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/usersTasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUsersTasks(
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        List<TaskResponse> taskResponse = taskService.getAll().stream()
                .filter(taskCommand -> TaskUtils.assignedToTask(taskCommand, userPrincipal))
                .map(taskMapper::taskCommandToTaskResponse)
                .toList();
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createTask(@RequestBody TaskRequest request) {
        TaskCommand taskCommand = taskMapper.taskRequestToTaskCommand(request);
        ProjectCommand convert = UUIDToProjectCommandConverter.convert(request.getProject());
        if (request.getStartDate().isBefore(convert.getStartDate())) {
            throw new NoSuchElementFoundException("Task cannot start before project start date");
        }
        taskCommand.setProject(convert);
        if (request.getAssigned() != null) {
            taskCommand.setAssigned(UUIDToUserCommandConverter.convert(request.getAssigned()));
        }
        if (request.getAssignedForGroup() != null) {
            taskCommand.setAssignedForGroup(UUIDToGroupCommandConverter.convert(request.getAssignedForGroup()));
        }
        if (request.getPhase() != null) {
            taskCommand.setPhase(UUIDToPhaseCommandConverter.convert(request.getPhase()));
        }
        TaskCommand createdProject = taskService.create(taskCommand);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tasks/{id}")
                .buildAndExpand(createdProject.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("Task was created successfully.")
                        .data(taskMapper.taskCommandToTaskResponse(createdProject))
                        .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        TaskCommand taskCommand = taskMapper.taskRequestToTaskCommand(request);
        ProjectCommand projectCommand = projectService.getByTasksId(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        if (request.getStartDate().isBefore(projectCommand.getStartDate())) {
            throw new NoSuchElementFoundException("Task cannot start before project start date");
        }
        if (request.getAssigned() != null) {
            taskCommand.setAssigned(UUIDToUserCommandConverter.convert(request.getAssigned()));
        }
        if (request.getAssignedForGroup() != null) {
            taskCommand.setAssignedForGroup(UUIDToGroupCommandConverter.convert(request.getAssignedForGroup()));
        }
        if (request.getPhase() != null) {
            taskCommand.setPhase(UUIDToPhaseCommandConverter.convert(request.getPhase()));
        }
        TaskCommand update = taskService.update(id, taskCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Task was successfully updated.")
                .data(taskMapper.taskCommandToTaskResponse(update))
                .build());
    }

    @PostMapping(path = "{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> addComment(
            @PathVariable UUID id,
            @Valid @RequestBody CommentRequest request,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        CommentCommand commentCommand = commentMapper.commentRequestToCommentCommand(request);
        commentCommand.setId(UUID.randomUUID());
        commentCommand.setUser(
                userService.getUser(userPrincipal.getUser().getId()).orElseThrow());
        TaskCommand updatedProject = taskService.addComment(id, commentCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully added.")
                .data(taskMapper.taskCommandToTaskResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = "{id}/comment/{idComment}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteComment(
            @PathVariable UUID id,
            @PathVariable UUID idComment,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        TaskCommand updatedProject = taskService.deleteComment(id, idComment);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully deleted.")
                .data(taskMapper.taskCommandToTaskResponse(updatedProject))
                .build());
    }
}
