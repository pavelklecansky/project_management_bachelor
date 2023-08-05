package cz.klecansky.projectmanagement.result.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeMapper;
import cz.klecansky.projectmanagement.result.io.ResultEntity;
import cz.klecansky.projectmanagement.result.io.ResultRepository;
import cz.klecansky.projectmanagement.result.shared.ResultCommand;
import cz.klecansky.projectmanagement.result.shared.ResultMapper;
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
public class ResultServiceImpl implements ResultService {

    @NonNull
    ResultRepository resultRepository;

    @NonNull
    ResultMapper resultMapper;

    @NonNull
    OutcomeMapper outcomeMapper;

    @Override
    public ResultCommand create(ResultCommand resultCommand) {
        resultCommand.setId(UUID.randomUUID());
        ResultEntity save = resultRepository.save(resultMapper.resultCommandToResultEntity(resultCommand));
        return resultMapper.resultEntityToResultCommand(save);
    }

    @Override
    public Optional<ResultCommand> get(UUID id) throws NoSuchElementFoundException {
        return resultRepository.findById(id).map(resultMapper::resultEntityToResultCommand);
    }

    @Override
    public void delete(UUID id) {
        resultRepository.deleteById(id);
    }

    @Override
    public ResultCommand update(UUID id, ResultCommand resultCommand) {
        ResultEntity resultEntity = resultRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        resultEntity.setName(resultCommand.getName());
        resultEntity.setDescription(resultCommand.getDescription());
        return resultMapper.resultEntityToResultCommand(resultRepository.save(resultEntity));
    }
}
