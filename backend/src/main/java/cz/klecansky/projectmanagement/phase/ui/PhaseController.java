package cz.klecansky.projectmanagement.phase.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PHASES_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.phase.service.PhaseService;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.phase.shared.PhaseMapper;
import cz.klecansky.projectmanagement.phase.ui.request.PhaseRequest;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.task.service.TaskService;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.task.shared.TaskMapper;
import java.net.URI;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(PHASES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PhaseController {

    @NonNull
    PhaseService phaseService;

    @NonNull
    ProjectService projectService;

    @NonNull
    TaskService taskService;

    @NonNull
    PhaseMapper phaseMapper;

    @NonNull
    TaskMapper taskMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhase(@PathVariable UUID id) throws NoSuchElementFoundException {
        return phaseService
                .get(id)
                .map(phaseCommand -> ResponseEntity.ok(phaseMapper.phaseCommandToPhaseResponse(phaseCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "project/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhases(@PathVariable UUID id) {
        ProjectCommand projectCommand = projectService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        return ResponseEntity.ok(projectCommand.getPhases().stream().map(phaseMapper::phaseCommandToPhaseResponse));
    }

    @GetMapping(path = "task/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhasesByTask(@PathVariable UUID id) {
        TaskCommand taskCommand = taskService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Task was not found");
        });
        return ResponseEntity.ok(
                taskCommand.getProject().getPhases().stream().map(phaseMapper::phaseCommandToPhaseResponse));
    }

    @GetMapping(path = "tasks/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhasesTasks(@PathVariable UUID id) {
        PhaseCommand phaseCommand = phaseService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Phase was not found");
        });
        return ResponseEntity.ok(phaseCommand.getTaskEntities().stream().map(taskMapper::taskCommandToTaskResponse));
    }

    @PostMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createPhase(@PathVariable UUID id, @RequestBody PhaseRequest request) {
        ProjectCommand projectCommand = projectService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        if (request.getStartDate().isBefore(projectCommand.getStartDate())) {
            throw new NoSuchElementFoundException("Phase cannot start before project start date");
        }
        PhaseCommand createPhase = phaseService.create(projectCommand, phaseMapper.phaseRequestToPhaseCommand(request));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/phases/{id}")
                .buildAndExpand(createPhase.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("Phase was successfully created.")
                        .data(phaseMapper.phaseCommandToPhaseResponse(createPhase))
                        .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deletePhase(@PathVariable UUID id) {
        phaseService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Phase was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updatePhase(@PathVariable UUID id, @RequestBody PhaseRequest request) {
        ProjectCommand projectCommand = projectService.getByPhaseId(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        if (request.getStartDate().isBefore(projectCommand.getStartDate())) {
            throw new NoSuchElementFoundException("Phase cannot start before project start date");
        }
        PhaseCommand update = phaseService.update(id, phaseMapper.phaseRequestToPhaseCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Phase was successfully updated.")
                .data(phaseMapper.phaseCommandToPhaseResponse(update))
                .build());
    }
}
