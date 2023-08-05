package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import java.util.Optional;
import java.util.UUID;

public interface OutcomeService {

    OutcomeCommand create(OutcomeCommand outcomeCommand);

    Optional<OutcomeCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    OutcomeCommand update(UUID id, OutcomeCommand outcomeCommand);
}
