package cz.klecansky.projectmanagement.comment.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;
import static cz.klecansky.projectmanagement.core.WebConstants.TASKS_API;

import cz.klecansky.projectmanagement.comment.service.CommentService;
import cz.klecansky.projectmanagement.comment.shared.CommentCreationCommand;
import cz.klecansky.projectmanagement.comment.ui.request.CommentCreationRequest;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import jakarta.validation.Valid;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentController {

    @NonNull
    CommentService commentService;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @NonNull
    TaskMapper taskMapper;

    @PostMapping(path = PROJECTS_API + "{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> addProjectComment(
            @PathVariable("id") UUID projectId,
            @Valid @RequestBody CommentCreationRequest request,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        CommentCreationCommand command = createCommentCreationCommand(
                request, projectId, userPrincipal.getUser().getId());
        ProjectCommand updatedProject = commentService.addProjectComment(command);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully added.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = PROJECTS_API + "{id}/comment/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<?>> deleteProjectComment(
            @PathVariable("id") UUID projectId, @PathVariable("commentId") UUID commentId) {
        ProjectCommand updatedProject = commentService.deleteProjectComment(projectId, commentId);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully deleted.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @PostMapping(path = TASKS_API + "{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> addTaskComment(
            @PathVariable("id") UUID taskId,
            @Valid @RequestBody CommentCreationRequest request,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        CommentCreationCommand command = createCommentCreationCommand(
                request, taskId, userPrincipal.getUser().getId());
        TaskCommand updatedProject = commentService.addTaskComment(command);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully added.")
                .data(taskMapper.taskCommandToTaskResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = TASKS_API + "{id}/comment/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<?>> deleteTaskComment(
            @PathVariable("id") UUID taskId, @PathVariable("commentId") UUID idComment) {
        TaskCommand updatedProject = commentService.deleteTaskComment(taskId, idComment);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully deleted.")
                .data(taskMapper.taskCommandToTaskResponse(updatedProject))
                .build());
    }

    private CommentCreationCommand createCommentCreationCommand(CommentCreationRequest request, UUID id, UUID userId) {
        return new CommentCreationCommand(request.text(), id, userId);
    }
}
