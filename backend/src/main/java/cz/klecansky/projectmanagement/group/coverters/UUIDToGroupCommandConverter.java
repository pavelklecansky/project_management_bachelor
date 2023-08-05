package cz.klecansky.projectmanagement.group.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.service.GroupService;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class UUIDToGroupCommandConverter implements Converter<UUID, GroupCommand> {

    @NonNull
    GroupService groupService;

    @Override
    public GroupCommand convert(UUID source) {
        return groupService.getGroup(source).orElseThrow(() -> new NoSuchElementFoundException("Group was not found."));
    }
}
