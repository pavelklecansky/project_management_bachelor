package cz.klecansky.projectmanagement.outcome.ui;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.outcome.service.OutcomeCategoryService;
import cz.klecansky.projectmanagement.outcome.service.OutcomeService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeMapper;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeRequest;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static cz.klecansky.projectmanagement.core.WebConstants.OUTCOMES_API;

@RestController
@RequestMapping(OUTCOMES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeController {

    @NonNull OutcomeService outcomeService;
    @NonNull OutcomeMapper outcomeMapper;
    @NonNull ProjectService projectService;
    @NonNull OutcomeCategoryService outcomeCategoryService;
    @NonNull Converter<UUID, PhaseCommand> UUIDToPhaseCommandConverter;
    @NonNull Converter<UUID, OutcomeCategoryCommand> uuidOutcomeCategoryCommandConverter;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcome(@PathVariable UUID id) throws NoSuchElementFoundException {
        return outcomeService.get(id)
                .map(outcomeCommand -> ResponseEntity.ok(outcomeMapper.outcomeCommandToOutcomeResponse(outcomeCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "project/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomes(@PathVariable UUID id) {
        ProjectCommand projectCommand = projectService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Project was not found");
        });
        return ResponseEntity.ok(projectCommand.getPhases().stream().map(PhaseCommand::getOutcomes).flatMap(List::stream).map(outcomeMapper::outcomeCommandToOutcomeResponse).collect(Collectors.toSet()));
    }

    @GetMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomesByCategory(@PathVariable UUID id) {
        OutcomeCategoryCommand outcomeCategoryCommand = outcomeCategoryService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Outcome category was not found");
        });
        return ResponseEntity.ok(outcomeCategoryCommand.getOutcomes().stream().map(outcomeMapper::outcomeCommandToOutcomeResponse).toList());
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createOutcome(@RequestBody OutcomeRequest request) {
        OutcomeCommand outcomeCommand = outcomeMapper.outcomeRequestToOutcomeCommand(request);
        PhaseCommand convert;
        try {
            convert = UUIDToPhaseCommandConverter.convert(request.getPhase());
            outcomeCommand.setPhase(convert);
        }catch (Exception e){
            throw new NoSuchElementFoundException("Phase cannot be empty.");
        }
        if (request.getStartDate().isBefore(convert.getStartDate())) {
            throw new NoSuchElementFoundException("Outcome cannot start before phase start date");
        }
        if (request.getOutcomeCategory() != null) {
            outcomeCommand.setOutcomeCategory(uuidOutcomeCategoryCommandConverter.convert(request.getOutcomeCategory()));
        }
        OutcomeCommand createOutcome = outcomeService.create(outcomeCommand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/outcomes/{id}")
                .buildAndExpand(createOutcome.getId()).toUri();
        return ResponseEntity.created(location).body(SuccessResponse.builder().message("Outcome was successfully created.").data(outcomeMapper.outcomeCommandToOutcomeResponse(createOutcome)).build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteOutcome(@PathVariable UUID id) {
        outcomeService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Outcome was successfully deleted.").build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateOutcome(@PathVariable UUID id, @RequestBody OutcomeRequest request) {
        OutcomeCommand outcomeCommand = outcomeMapper.outcomeRequestToOutcomeCommand(request);
        PhaseCommand convert;
        try {
            convert = UUIDToPhaseCommandConverter.convert(request.getPhase());
            outcomeCommand.setPhase(convert);
        }catch (Exception e){
            throw new NoSuchElementFoundException("Phase cannot be empty.");
        }
        if (request.getStartDate().isBefore(convert.getStartDate())) {
            throw new NoSuchElementFoundException("Outcome cannot start before phase start date");
        }
        if (request.getOutcomeCategory() != null) {
            outcomeCommand.setOutcomeCategory(uuidOutcomeCategoryCommandConverter.convert(request.getOutcomeCategory()));
        }
        OutcomeCommand update = outcomeService.update(id, outcomeCommand);
        return ResponseEntity.ok(SuccessResponse.builder().message("Outcome was successfully updated.").data(outcomeMapper.outcomeCommandToOutcomeResponse(update)).build());
    }
}
