package cz.klecansky.projectmanagement.project.ui;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.comment.shared.CommentMapper;
import cz.klecansky.projectmanagement.comment.ui.request.CommentRequest;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.group.service.GroupService;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
import cz.klecansky.projectmanagement.project.ui.request.AddGroupMemberRequest;
import cz.klecansky.projectmanagement.project.ui.request.AddMemberRequest;
import cz.klecansky.projectmanagement.project.ui.request.ProjectRequest;
import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import cz.klecansky.projectmanagement.project.utils.ProjectUtils;
import cz.klecansky.projectmanagement.security.SecurityUtils;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.storage.service.StorageService;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;

@RestController
@RequestMapping(PROJECTS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class  ProjectController {

    @NonNull ProjectService projectService;
    @NonNull UserService userService;
    @NonNull GroupService groupService;
    @NonNull ProjectMapper projectMapper;
    @NonNull CommentMapper commentMapper;
    @NonNull StorageService storageService;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProject(@PathVariable UUID id) throws NoSuchElementFoundException {
        return projectService.get(id)
                .map(projectCommand -> ResponseEntity.ok(projectMapper.projectCommandToProjectResponse(projectCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProjects(@CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        List<ProjectResponse> projectResponses = projectService.getAll().stream().filter(projectCommand -> SecurityUtils.isAdmin(userPrincipal) || ProjectUtils.memberOfProject(projectCommand,userPrincipal)).map(
                projectMapper::projectCommandToProjectResponse).toList();
        return ResponseEntity.ok(projectResponses);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        ProjectCommand createdProject = projectService.create(projectMapper.projectRequestToProjectCommand(request));
        storageService.createDictionaryInRoot(createdProject.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/projects/{id}")
                .buildAndExpand(createdProject.getId()).toUri();
        return ResponseEntity.created(location).body(SuccessResponse.builder().message("Project was successfully created.").data(projectMapper.projectCommandToProjectResponse(createdProject)).build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteProject(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Project was successfully deleted.").build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateProject(@PathVariable UUID id, @RequestBody ProjectRequest request) {
        ProjectCommand update = projectService.update(id, projectMapper.projectRequestToProjectCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder().message("Project was successfully updated.").data(projectMapper.projectCommandToProjectResponse(update)).build());
    }

    @PostMapping(path = "{id}/member")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> addMember(@PathVariable UUID id, @Valid @RequestBody AddMemberRequest request) {
        UserCommand userCommand = userService.getUser(request.getUser()).orElseThrow();
        ProjectCommand updatedProject = projectService.addMember(id, userCommand);
        return ResponseEntity.ok(SuccessResponse.builder().message("Member was successfully added.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

    @DeleteMapping(path = "{id}/member/{idMember}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteMember(@PathVariable UUID id, @PathVariable UUID idMember) {
        ProjectCommand updatedProject = projectService.deleteMember(id, idMember);
        return ResponseEntity.ok(SuccessResponse.builder().message("Member was successfully deleted.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

    @PostMapping(path = "{id}/group-member")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> addGroupMember(@PathVariable UUID id, @Valid @RequestBody AddGroupMemberRequest request) {
        GroupCommand groupCommand = groupService.getGroup(request.getGroup()).orElseThrow();
        ProjectCommand updatedProject = projectService.addGroupMember(id, groupCommand);
        return ResponseEntity.ok(SuccessResponse.builder().message("Group member was successfully added.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

    @DeleteMapping(path = "{id}/group-member/{idMember}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteGroupMember(@PathVariable UUID id, @PathVariable UUID idMember) {
        ProjectCommand updatedProject = projectService.deleteGroupMember(id, idMember);
        return ResponseEntity.ok(SuccessResponse.builder().message("Group member was successfully deleted.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

    @PostMapping(path = "{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> addComment(@PathVariable UUID id, @Valid @RequestBody CommentRequest request, @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        CommentCommand commentCommand = commentMapper.commentRequestToCommentCommand(request);
        commentCommand.setId(UUID.randomUUID());
        commentCommand.setUser(userService.getUser(userPrincipal.getUser().getId()).orElseThrow());
        ProjectCommand updatedProject = projectService.addComment(id, commentCommand);
        return ResponseEntity.ok(SuccessResponse.builder().message("Comment was successfully added.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

    @DeleteMapping(path = "{id}/comment/{idComment}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable UUID id, @PathVariable UUID idComment) {
        ProjectCommand updatedProject = projectService.deleteComment(id, idComment);
        return ResponseEntity.ok(SuccessResponse.builder().message("Comment was successfully deleted.").data(projectMapper.projectCommandToProjectResponse(updatedProject)).build());
    }

}
