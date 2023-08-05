package cz.klecansky.projectmanagement.result.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.result.shared.ResultCommand;
import java.util.Optional;
import java.util.UUID;

public interface ResultService {

    ResultCommand create(ResultCommand resultCommand);

    Optional<ResultCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    ResultCommand update(UUID id, ResultCommand resultCommand);
}
