package cz.klecansky.projectmanagement.user.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.user.exception.ExpiredTokenException;
import cz.klecansky.projectmanagement.user.io.entity.PasswordResetTokenEntity;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.repository.PasswordResetTokenRepository;
import cz.klecansky.projectmanagement.user.io.repository.UserRepository;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class PasswordResetTokenService {

    @NonNull PasswordResetTokenRepository passwordResetTokenRepository;
    @NonNull UserRepository userRepository;
    @NonNull UserMapper userMapper;

    public void savePasswordResetToken(PasswordResetTokenEntity token) {
        passwordResetTokenRepository.save(token);
    }

    public Optional<PasswordResetTokenEntity> getToken(UUID token) {
        return passwordResetTokenRepository.findById(token);
    }

    public UserCommand resetPassword(UUID token) {
        PasswordResetTokenEntity tokenEntity = getToken(token).orElseThrow(NoSuchElementFoundException::new);
        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            passwordResetTokenRepository.delete(tokenEntity);
            throw new ExpiredTokenException("Token is expired please request new one.");
        }
        UserEntity user = tokenEntity.getUser();
        user.setEmailVerified(true);
        passwordResetTokenRepository.delete(tokenEntity);
        return userMapper.userEntityToUserCommand(userRepository.save(user));
    }
}
