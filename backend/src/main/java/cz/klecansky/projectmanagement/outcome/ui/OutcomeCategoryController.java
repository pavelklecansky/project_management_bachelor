package cz.klecansky.projectmanagement.outcome.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.OUTCOME_CATEGORIES_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.outcome.service.OutcomeCategoryService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryOldMapper;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryUpsertCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeMapper;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeCategoryUpsertRequest;
import io.jsonwebtoken.lang.Assert;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(OUTCOME_CATEGORIES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeCategoryController {

    @NonNull
    OutcomeCategoryService outcomeCategoryService;

    @NonNull
    OutcomeCategoryOldMapper outcomeCategoryOldMapper;

    @NonNull
    OutcomeMapper outcomeMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomeCategory(@PathVariable("id") UUID outcomeCategoryId)
            throws NoSuchElementFoundException {
        return outcomeCategoryService
                .get(outcomeCategoryId)
                .map(outcomeCategoryCommand ->
                        ResponseEntity.ok(outcomeCategoryOldMapper.outcomeCategoryCommandToOutcomeCategoryResponse(
                                outcomeCategoryCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "project/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOutcomeCategories(@PathVariable("id") UUID projectId) {
        return ResponseEntity.ok(outcomeCategoryService.getOutcomesCategoryByProjectId(projectId).stream()
                .map(outcomeCategoryOldMapper::outcomeCategoryCommandToOutcomeCategoryResponse)
                .toList());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> createOutcomeCategory(@RequestBody OutcomeCategoryUpsertRequest request) {
        OutcomeCategoryUpsertCommand outcomeCategoryCommand =
                outcomeMapper.outcomeCategoryUpsertRequestToOutcomeCategoryUpsertCommand(request);
        OutcomeCategoryCommand createOutcome = outcomeCategoryService.upsert(outcomeCategoryCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome Category was successfully created.")
                .data(outcomeCategoryOldMapper.outcomeCategoryCommandToOutcomeCategoryResponse(createOutcome))
                .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteOutcomeCategory(@PathVariable UUID id) {
        outcomeCategoryService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome Category was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> updateOutcomeCategory(
            @PathVariable UUID id, @RequestBody OutcomeCategoryUpsertRequest request) {
        Assert.state(id.equals(request.id()), "Path variable and body ids of outcome category should equal.");
        OutcomeCategoryUpsertCommand outcomeCategoryCommand =
                outcomeMapper.outcomeCategoryUpsertRequestToOutcomeCategoryUpsertCommand(request);
        OutcomeCategoryCommand update = outcomeCategoryService.upsert(outcomeCategoryCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Outcome Category was successfully updated.")
                .data(outcomeCategoryOldMapper.outcomeCategoryCommandToOutcomeCategoryResponse(update))
                .build());
    }
}
