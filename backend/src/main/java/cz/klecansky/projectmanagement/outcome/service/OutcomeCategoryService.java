package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryUpsertCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutcomeCategoryService {

    List<OutcomeCategoryCommand> getOutcomesCategoryByProjectId(UUID id);

    OutcomeCategoryCommand upsert(OutcomeCategoryUpsertCommand outcomeCategoryCommand);

    Optional<OutcomeCategoryCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    OutcomeCategoryCommand update(UUID id, OutcomeCategoryCommand outcomeCommand);
}
