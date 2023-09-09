package cz.klecansky.projectmanagement.project.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
import cz.klecansky.projectmanagement.project.ui.request.ProjectRequest;
import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import cz.klecansky.projectmanagement.projectmember.utils.MemberUtils;
import cz.klecansky.projectmanagement.security.SecurityUtils;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.storage.service.StorageService;
import jakarta.validation.Valid;
import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(PROJECTS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectController {

    @NonNull
    ProjectService projectService;

    @NonNull
    ProjectMapper projectMapper;

    @NonNull
    StorageService storageService;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProject(@PathVariable UUID id) throws NoSuchElementFoundException {
        return projectService
                .get(id)
                .map(projectCommand -> ResponseEntity.ok(projectMapper.projectCommandToProjectResponse(projectCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProjects(
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        List<ProjectResponse> projectResponses = projectService.getAll().stream()
                .filter(projectCommand -> SecurityUtils.isAdmin(userPrincipal)
                        || MemberUtils.memberOfProject(projectCommand, userPrincipal))
                .map(projectMapper::projectCommandToProjectResponse)
                .toList();
        return ResponseEntity.ok(projectResponses);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        ProjectCommand createdProject = projectService.create(projectMapper.projectRequestToProjectCommand(request));
        storageService.createDictionaryInRoot(createdProject.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/projects/{id}")
                .buildAndExpand(createdProject.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("Project was successfully created.")
                        .data(projectMapper.projectCommandToProjectResponse(createdProject))
                        .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteProject(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Project was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateProject(@PathVariable UUID id, @RequestBody ProjectRequest request) {
        ProjectCommand update = projectService.update(id, projectMapper.projectRequestToProjectCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Project was successfully updated.")
                .data(projectMapper.projectCommandToProjectResponse(update))
                .build());
    }
}
