package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryRepository;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeCategoryServiceImpl implements OutcomeCategoryService {

    @NonNull OutcomeCategoryRepository outcomeCategoryRepository;
    @NonNull OutcomeCategoryMapper outcomeCategoryMapper;

    @Override
    public OutcomeCategoryCommand create(OutcomeCategoryCommand outcomeCategoryCommand) {
        outcomeCategoryCommand.setId(UUID.randomUUID());
        OutcomeCategoryEntity save = outcomeCategoryRepository.save(outcomeCategoryMapper.outcomeCategoryCommandToOutcomeCategoryEntity(outcomeCategoryCommand));
        return outcomeCategoryMapper.outcomeCategoryEntityToOutcomeCategoryCommand(save);
    }

    @Override
    public Optional<OutcomeCategoryCommand> get(UUID id) throws NoSuchElementFoundException {
        return outcomeCategoryRepository.findById(id).map(outcomeCategoryMapper::outcomeCategoryEntityToOutcomeCategoryCommand);
    }

    @Override
    public void delete(UUID id) {
        outcomeCategoryRepository.deleteById(id);
    }

    @Override
    public OutcomeCategoryCommand update(UUID id, OutcomeCategoryCommand outcomeCommand) {
        OutcomeCategoryEntity outcomeCategoryEntity = outcomeCategoryRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        outcomeCategoryEntity.setName(outcomeCommand.getName());
        outcomeCategoryEntity.setDescription(outcomeCommand.getDescription());
        return outcomeCategoryMapper.outcomeCategoryEntityToOutcomeCategoryCommand(outcomeCategoryRepository.save(outcomeCategoryEntity));
    }
}
