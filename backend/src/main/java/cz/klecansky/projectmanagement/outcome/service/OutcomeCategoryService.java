package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;

import java.util.Optional;
import java.util.UUID;

public interface OutcomeCategoryService {

    OutcomeCategoryCommand create(OutcomeCategoryCommand outcomeCommand);

    Optional<OutcomeCategoryCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    OutcomeCategoryCommand update(UUID id, OutcomeCategoryCommand outcomeCommand);
}
