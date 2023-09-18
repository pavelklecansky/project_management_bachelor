package cz.klecansky.projectmanagement.project.io;

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
public class ProjectLoader implements ByIdLoader<ProjectEntity, UUID> {

    @NonNull
    ProjectRepository repository;

    @Override
    public ProjectEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Project was not found."));
    }

    @Override
    public Optional<ProjectEntity> findById(@NonNull UUID id) {
        return repository.findById(id);
    }
}
