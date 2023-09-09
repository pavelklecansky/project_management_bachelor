package cz.klecansky.projectmanagement.user.io.repository;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
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
public class UserLoader implements ByIdLoader<UUID, UserEntity> {

    @NonNull
    UserRepository repository;

    @Override
    public UserEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("User was not found."));
    }

    @Override
    public Optional<UserEntity> findById(@NonNull UUID id) throws NoSuchElementFoundException {
        return repository.findById(id);
    }
}
