package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeUpsertCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutcomeService {

    OutcomeCommand upsert(OutcomeUpsertCommand outcomeCommand);

    Optional<OutcomeCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    List<OutcomeCommand> getOutcomesByProjectId(UUID id);

    List<OutcomeCommand> getOutcomesByCategoryId(UUID id);
}
