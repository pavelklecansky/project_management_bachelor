package cz.klecansky.projectmanagement.comment.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;

import cz.klecansky.projectmanagement.comment.service.CommentService;
import cz.klecansky.projectmanagement.comment.shared.CommentCreationCommand;
import cz.klecansky.projectmanagement.comment.ui.request.CommentCreationRequest;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
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
@RequestMapping(PROJECTS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentController {

    @NonNull
    CommentService commentService;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @PostMapping(path = "{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> addComment(
            @PathVariable("id") UUID projectId,
            @Valid @RequestBody CommentCreationRequest request,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        CommentCreationCommand command = createCommentCreationCommand(
                request, projectId, userPrincipal.getUser().getId());
        ProjectCommand updatedProject = commentService.addComment(command);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully added.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = "{id}/comment/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<?>> deleteComment(
            @PathVariable("id") UUID projectId, @PathVariable("commentId") UUID commentId) {
        ProjectCommand updatedProject = commentService.deleteComment(projectId, commentId);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Comment was successfully deleted.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    private CommentCreationCommand createCommentCreationCommand(
            CommentCreationRequest request, UUID projectId, UUID userId) {
        return new CommentCreationCommand(request.text(), projectId, userId);
    }
}
