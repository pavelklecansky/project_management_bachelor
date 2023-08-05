package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.io.OutcomeEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeRepository;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryMapper;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeMapper;
import cz.klecansky.projectmanagement.phase.shared.PhaseMapper;
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
    OutcomeMapper outcomeMapper;

    @NonNull
    PhaseMapper phaseMapper;

    @NonNull
    OutcomeCategoryMapper outcomeCategoryMapper;

    @Override
    public OutcomeCommand create(OutcomeCommand outcomeCommand) {
        outcomeCommand.setId(UUID.randomUUID());
        OutcomeEntity save = outcomeRepository.save(outcomeMapper.outcomeCommandToOutcomeEntity(outcomeCommand));
        return outcomeMapper.outcomeEntityToOutcomeCommand(save);
    }

    @Override
    public Optional<OutcomeCommand> get(UUID id) throws NoSuchElementFoundException {
        return outcomeRepository.findById(id).map(outcomeMapper::outcomeEntityToOutcomeCommand);
    }

    @Override
    public void delete(UUID id) {
        outcomeRepository.deleteById(id);
    }

    @Override
    public OutcomeCommand update(UUID id, OutcomeCommand outcomeCommand) {
        OutcomeEntity outcomeEntity = outcomeRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        outcomeEntity.setName(outcomeCommand.getName());
        outcomeEntity.setDescription(outcomeCommand.getDescription());
        outcomeEntity.setStartDate(outcomeCommand.getStartDate());
        outcomeEntity.setEndDate(outcomeCommand.getEndDate());
        outcomeEntity.setPhase(phaseMapper.phaseCommandToPhaseEntity(outcomeCommand.getPhase()));
        if (outcomeCommand.getOutcomeCategory() != null) {
            outcomeEntity.setOutcomeCategory(outcomeCategoryMapper.outcomeCategoryCommandToOutcomeCategoryEntity(
                    outcomeCommand.getOutcomeCategory()));
        } else {
            outcomeEntity.setOutcomeCategory(null);
        }
        return outcomeMapper.outcomeEntityToOutcomeCommand(outcomeRepository.save(outcomeEntity));
    }
}
