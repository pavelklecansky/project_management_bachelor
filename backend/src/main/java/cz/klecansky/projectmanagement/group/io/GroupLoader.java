package cz.klecansky.projectmanagement.group.io;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
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
public class GroupLoader implements ByIdLoader<GroupEntity, UUID> {

    @NonNull
    GroupRepository groupRepository;

    @Override
    public GroupEntity getById(@NonNull UUID id) throws NoSuchElementFoundException {
        return findById(id).orElseThrow(() -> new NoSuchElementFoundException("Group was not found."));
    }

    @Override
    public Optional<GroupEntity> findById(@NonNull UUID id) {
        return groupRepository.findById(id);
    }
}
