package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryLoader;
import cz.klecansky.projectmanagement.outcome.io.OutcomeEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeRepository;
import cz.klecansky.projectmanagement.outcome.shared.*;
import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeServiceImpl implements OutcomeService {

    @NonNull
    OutcomeRepository outcomeRepository;

    @NonNull
    OutcomeMapperOld outcomeMapperOld;

    @NonNull
    ProjectService projectService;

    @NonNull
    OutcomeCategoryService outcomeCategoryService;

    @NonNull
    ByIdLoader<PhaseEntity, UUID> phaseLoader;

    @NonNull
    OutcomeCategoryLoader outcomeCategoryLoader;

    @Override
    public OutcomeCommand upsert(OutcomeUpsertCommand outcomeCommand) {
        OutcomeEntity save = outcomeRepository.save(upsertOutcome(outcomeCommand));
        return outcomeMapperOld.outcomeEntityToOutcomeCommand(save);
    }

    @Override
    public Optional<OutcomeCommand> get(UUID id) throws NoSuchElementFoundException {
        return outcomeRepository.findById(id).map(outcomeMapperOld::outcomeEntityToOutcomeCommand);
    }

    @Override
    public void delete(UUID id) {
        outcomeRepository.deleteById(id);
    }

    @Override
    public List<OutcomeCommand> getOutcomesByProjectId(UUID id) {
        ProjectCommand projectCommand =
                projectService.get(id).orElseThrow(() -> new NoSuchElementFoundException("Project was not found"));
        return projectCommand.getPhases().stream()
                .map(PhaseCommand::getOutcomes)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<OutcomeCommand> getOutcomesByCategoryId(UUID id) {
        OutcomeCategoryCommand outcomeCategoryCommand = outcomeCategoryService
                .get(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Outcome category was not found"));
        return outcomeCategoryCommand.getOutcomes();
    }

    private OutcomeEntity upsertOutcome(OutcomeUpsertCommand outcomeCommand) {
        if (outcomeCommand.id() == null) {
            return createNewOutcome(outcomeCommand);
        }
        return updateExistingOutcome(outcomeCommand);
    }

    private OutcomeEntity createNewOutcome(OutcomeUpsertCommand outcomeCommand) {
        PhaseEntity phase = loadPhaseAndValidate(outcomeCommand);
        OutcomeCategoryEntity outcomeCategory = outcomeCommand.outcomeCategoryId() != null
                ? outcomeCategoryLoader.getById(outcomeCommand.outcomeCategoryId())
                : null;
        return new OutcomeEntity(
                UUID.randomUUID(),
                outcomeCommand.name(),
                outcomeCommand.startDate(),
                outcomeCommand.endDate(),
                outcomeCommand.description(),
                outcomeCategory,
                phase,
                List.of());
    }

    private OutcomeEntity updateExistingOutcome(OutcomeUpsertCommand command) {
        OutcomeEntity outcomeEntity = outcomeRepository
                .findById(command.id())
                .orElseThrow(() -> new NoSuchElementFoundException("Cannot update non existing outcome"));
        if (command.name() != null) {
            outcomeEntity = outcomeEntity.withName(command.name());
        }
        if (command.startDate() != null) {
            outcomeEntity = outcomeEntity.withStartDate(command.startDate());
        }
        if (command.endDate() != null) {
            outcomeEntity = outcomeEntity.withEndDate(command.endDate());
        }
        if (command.description() != null) {
            outcomeEntity = outcomeEntity.withDescription(command.description());
        }
        if (command.phaseId() != null) {
            PhaseEntity phaseEntity = loadPhaseAndValidate(command);
            outcomeEntity = outcomeEntity.withPhase(phaseEntity);
        }
        if (command.outcomeCategoryId() != null) {
            OutcomeCategoryEntity outcomeCategory = outcomeCategoryLoader.getById(command.outcomeCategoryId());
            outcomeEntity = outcomeEntity.withOutcomeCategory(outcomeCategory);
        }
        return outcomeEntity;
    }

    private PhaseEntity loadPhaseAndValidate(OutcomeUpsertCommand outcomeCommand) {
        PhaseEntity phase = phaseLoader.getById(outcomeCommand.phaseId());
        if (outcomeCommand.startDate().isBefore(phase.getStartDate())) {
            throw new NoSuchElementFoundException("Outcome cannot start before phase start date");
        }
        return phase;
    }
}
