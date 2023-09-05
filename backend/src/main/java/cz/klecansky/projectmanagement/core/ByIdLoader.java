package cz.klecansky.projectmanagement.core;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import lombok.NonNull;

public interface ByIdLoader<ID, VALUE> {

    VALUE getById(@NonNull ID id) throws NoSuchElementFoundException;
}
