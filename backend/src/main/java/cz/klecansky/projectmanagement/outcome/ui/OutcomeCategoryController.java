package cz.klecansky.projectmanagement.outcome.ui;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.outcome.service.OutcomeCategoryService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryMapper;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeCategoryRequest;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

import static cz.klecansky.projectmanagement.core.WebConstants.OUTCOME_CATEGORIES_API;

@RestController
@RequestMapping(OUTCOME_CATEGORIES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeCategoryController {

    @NonNull OutcomeCategoryService outcomeCategoryService;
    @NonNull OutcomeCategoryMapper outcomeCategoryMapper;
    @NonNull ProjectService projectService;
    @NonNull Converter<UUID, ProjectCommand> uuidProjectCommandConverter;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomeCategory(@PathVariable UUID id) throws NoSuchElementFoundException {
        return outcomeCategoryService.get(id)
                .map(outcomeCategoryCommand -> ResponseEntity.ok(outcomeCategoryMapper.outcomeCategoryCommandToOutcomeCategoryResponse(outcomeCategoryCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "project/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomeCategories(@PathVariable UUID id) {
        ProjectCommand projectCommand = projectService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        return ResponseEntity.ok(projectCommand.getOutcomeCategories().stream().map(outcomeCategoryMapper::outcomeCategoryCommandToOutcomeCategoryResponse).collect(Collectors.toSet()));
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createOutcomeCategory(@RequestBody OutcomeCategoryRequest request) {
        OutcomeCategoryCommand outcomeCategoryCommand = outcomeCategoryMapper.outcomeCategoryRequestToOutcomeCategoryCommand(request);
        outcomeCategoryCommand.setProject(uuidProjectCommandConverter.convert(request.getProject()));
        OutcomeCategoryCommand createOutcome = outcomeCategoryService.create(outcomeCategoryCommand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/outcome-categories/{id}")
                .buildAndExpand(createOutcome.getId()).toUri();
        return ResponseEntity.created(location).body(SuccessResponse.builder().message("Outcome Category was successfully created.").data(outcomeCategoryMapper.outcomeCategoryCommandToOutcomeCategoryResponse(
                createOutcome)).build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteOutcomeCategory(@PathVariable UUID id) {
        outcomeCategoryService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Outcome Category was successfully deleted.").build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateOutcomeCategory(@PathVariable UUID id, @RequestBody OutcomeCategoryRequest request) {
        OutcomeCategoryCommand outcomeCategoryCommand = outcomeCategoryMapper.outcomeCategoryRequestToOutcomeCategoryCommand(request);
        OutcomeCategoryCommand update = outcomeCategoryService.update(id, outcomeCategoryCommand);
        return ResponseEntity.ok(SuccessResponse.builder().message("Outcome Category was successfully updated.").data(outcomeCategoryMapper.outcomeCategoryCommandToOutcomeCategoryResponse(update)).build());
    }
}
