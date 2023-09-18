package cz.klecansky.projectmanagement.task.io;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskLoader implements ByIdLoader<TaskEntity, UUID> {

    @NonNull
    TaskRepository repository;

    @Override
    public TaskEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return findById(id).orElseThrow(() -> new NoSuchElementFoundException("Task was not found."));
    }

    @Override
    public Optional<TaskEntity> findById(@NonNull UUID id) {
        return repository.findById(id);
    }
}
