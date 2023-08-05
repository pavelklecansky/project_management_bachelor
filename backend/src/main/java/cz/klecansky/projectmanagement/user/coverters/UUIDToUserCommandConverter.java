package cz.klecansky.projectmanagement.user.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
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
public class UUIDToUserCommandConverter implements Converter<UUID, UserCommand> {

    @NonNull
    UserService userService;

    @Override
    public UserCommand convert(UUID source) {
        return userService.getUser(source).orElseThrow(() -> new NoSuchElementFoundException("User was not found."));
    }
}
