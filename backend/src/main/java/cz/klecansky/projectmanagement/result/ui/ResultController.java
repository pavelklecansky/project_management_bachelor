package cz.klecansky.projectmanagement.result.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.RESULTS_CATEGORIES_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.outcome.service.OutcomeService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import cz.klecansky.projectmanagement.result.service.ResultService;
import cz.klecansky.projectmanagement.result.shared.ResultCommand;
import cz.klecansky.projectmanagement.result.shared.ResultMapper;
import cz.klecansky.projectmanagement.result.ui.request.ResultRequest;
import cz.klecansky.projectmanagement.storage.service.StorageService;
import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(RESULTS_CATEGORIES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ResultController {

    @NonNull
    ResultService resultService;

    @NonNull
    ResultMapper resultMapper;

    @NonNull
    OutcomeService outcomeService;

    @NonNull
    StorageService storageService;

    @NonNull
    Converter<UUID, OutcomeCommand> uuidOutcomeCommandConverter;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getResult(@PathVariable UUID id) throws NoSuchElementFoundException {
        return resultService
                .get(id)
                .map(resultCommand -> ResponseEntity.ok(resultMapper.resultCommandToResultResponse(resultCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping(path = "outcome/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getResultOfOutcome(@PathVariable UUID id) {
        OutcomeCommand outcomeCommand = outcomeService.get(id).orElseThrow(() -> {
            throw new NoSuchElementFoundException("Outcome was not found");
        });
        return ResponseEntity.ok(outcomeCommand.getResults().stream()
                .map(resultMapper::resultCommandToResultResponse)
                .collect(Collectors.toSet()));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> createResult(@RequestBody ResultRequest request) {
        ResultCommand resultCommand = resultMapper.resultRequestToResultCommand(request);
        resultCommand.setOutcome(uuidOutcomeCommandConverter.convert(request.getOutcome()));
        ResultCommand createdResult = resultService.create(resultCommand);
        storageService.createDictionaryInRoot(createdResult.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/results/{id}")
                .buildAndExpand(createdResult.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("Result was successfully created.")
                        .data(resultMapper.resultCommandToResultResponse(createdResult))
                        .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> deleteResult(@PathVariable UUID id) {
        resultService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Result was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> updateResult(@PathVariable UUID id, @RequestBody ResultRequest request) {
        ResultCommand resultCommand = resultMapper.resultRequestToResultCommand(request);
        ResultCommand update = resultService.update(id, resultCommand);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Result was successfully updated.")
                .data(resultMapper.resultCommandToResultResponse(update))
                .build());
    }
}
