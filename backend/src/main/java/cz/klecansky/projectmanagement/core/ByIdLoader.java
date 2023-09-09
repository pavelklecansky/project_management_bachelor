package cz.klecansky.projectmanagement.core;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import java.util.Optional;
import lombok.NonNull;

public interface ByIdLoader<ID, VALUE> {

    VALUE getById(@NonNull ID id) throws NoSuchElementFoundException;

    Optional<VALUE> findById(@NonNull ID id) throws NoSuchElementFoundException;
}
