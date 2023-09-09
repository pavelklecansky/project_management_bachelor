package cz.klecansky.projectmanagement.project.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectUpsertCommand;
import cz.klecansky.projectmanagement.project.ui.request.ProjectUpsertRequest;
import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import io.jsonwebtoken.lang.Assert;
import jakarta.validation.Valid;
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
@RequestMapping(PROJECTS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectController {

    @NonNull
    ProjectService projectService;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProject(@PathVariable UUID id) throws NoSuchElementFoundException {
        return projectService
                .get(id)
                .map(projectCommand ->
                        ResponseEntity.ok(oldProjectMapper.projectCommandToProjectResponse(projectCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProjectResponse> getProjects(
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        return projectService.getUsersProjects(userPrincipal).stream()
                .map(oldProjectMapper::projectCommandToProjectResponse)
                .toList();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> createProject(@RequestBody @Valid ProjectUpsertRequest request) {
        ProjectCommand createdProject = projectService.create(createUpsertCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Project was successfully created.")
                .data(oldProjectMapper.projectCommandToProjectResponse(createdProject))
                .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteProject(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Project was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> updateProject(
            @PathVariable UUID id, @RequestBody ProjectUpsertRequest request) {
        Assert.state(id.equals(request.id()), "Path variable and body ids of outcome should equal.");
        ProjectCommand update = projectService.update(createUpsertCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Project was successfully updated.")
                .data(oldProjectMapper.projectCommandToProjectResponse(update))
                .build());
    }

    private ProjectUpsertCommand createUpsertCommand(ProjectUpsertRequest request) {
        UUID projectId = request.id() == null ? UUID.randomUUID() : request.id();
        return new ProjectUpsertCommand(
                projectId, request.name(), request.description(), request.startDate(), request.endDate());
    }
}
