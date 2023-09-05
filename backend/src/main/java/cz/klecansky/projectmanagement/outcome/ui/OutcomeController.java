package cz.klecansky.projectmanagement.outcome.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.OUTCOMES_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.outcome.service.OutcomeService;
import cz.klecansky.projectmanagement.outcome.shared.*;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeUpsertRequest;
import io.jsonwebtoken.lang.Assert;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(OUTCOMES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeController {

    @NonNull
    OutcomeService outcomeService;

    @NonNull
    OutcomeMapperOld outcomeMapperOld;

    @NonNull
    OutcomeMapper outcomeMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcome(@PathVariable("id") UUID outcomeId) throws NoSuchElementFoundException {
        return outcomeService
                .get(outcomeId)
                .map(outcomeCommand ->
                        ResponseEntity.ok(outcomeMapperOld.outcomeCommandToOutcomeResponse(outcomeCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "project/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomes(@PathVariable("id") UUID projectId) {
        return ResponseEntity.ok(outcomeService.getOutcomesByProjectId(projectId).stream()
                .map(outcomeMapperOld::outcomeCommandToOutcomeResponse)
                .collect(Collectors.toSet()));
    }

    @GetMapping(path = "category/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomesByCategory(@PathVariable("id") UUID outcomeCategoryId) {
        return ResponseEntity.ok(outcomeService.getOutcomesByCategoryId(outcomeCategoryId).stream()
                .map(outcomeMapperOld::outcomeCommandToOutcomeResponse)
                .toList());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> createOutcome(@RequestBody OutcomeUpsertRequest request) {
        OutcomeUpsertCommand outcomeCommand = outcomeMapper.outcomeUpsertRequestToOutcomeUpsertCommand(request);
        OutcomeCommand createOutcome = outcomeService.upsert(outcomeCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome was successfully created.")
                .data(outcomeMapperOld.outcomeCommandToOutcomeResponse(createOutcome))
                .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteOutcome(@PathVariable UUID id) {
        outcomeService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> updateOutcome(
            @PathVariable UUID id, @RequestBody OutcomeUpsertRequest request) {
        Assert.state(id.equals(request.id()), "Path variable and body ids of outcome should equal.");
        OutcomeUpsertCommand outcomeCommand = outcomeMapper.outcomeUpsertRequestToOutcomeUpsertCommand(request);
        OutcomeCommand update = outcomeService.upsert(outcomeCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome was successfully updated.")
                .data(outcomeMapperOld.outcomeCommandToOutcomeResponse(update))
                .build());
    }
}
