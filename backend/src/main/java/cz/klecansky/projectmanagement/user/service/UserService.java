package cz.klecansky.projectmanagement.user.service;

import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCommand createUser(UserCommand userCommand, String passcode);

    Optional<UserCommand> getUser(UUID userId);

    Optional<UserCommand> getUserByEmail(String email);

    UserCommand updateUser(UUID userId, UserCommand user);

    void deleteUser(UUID userId);

    List<UserCommand> getUsers();

    UserCommand newPassword(UUID passwordResetToken, String newPassword);

    UserCommand changePassword(UserPrincipal user, String currentPassword, String newPassword);
}
