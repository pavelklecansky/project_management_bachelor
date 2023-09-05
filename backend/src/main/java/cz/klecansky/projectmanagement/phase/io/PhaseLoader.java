package cz.klecansky.projectmanagement.phase.io;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PhaseLoader implements ByIdLoader<UUID, PhaseEntity> {

    @NonNull
    PhaseRepository repository;

    @Override
    public PhaseEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Phase was not found."));
    }
}