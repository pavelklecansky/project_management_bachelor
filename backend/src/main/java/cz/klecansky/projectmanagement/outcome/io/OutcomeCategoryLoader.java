package cz.klecansky.projectmanagement.outcome.io;

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
public class OutcomeCategoryLoader implements ByIdLoader<UUID, OutcomeCategoryEntity> {

    @NonNull
    OutcomeCategoryRepository repository;

    @Override
    public OutcomeCategoryEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return findById(id).orElseThrow(() -> new NoSuchElementFoundException("Outcome category was not found."));
    }

    @Override
    public Optional<OutcomeCategoryEntity> findById(@NonNull UUID id) throws NoSuchElementFoundException {
        return repository.findById(id);
    }
}
