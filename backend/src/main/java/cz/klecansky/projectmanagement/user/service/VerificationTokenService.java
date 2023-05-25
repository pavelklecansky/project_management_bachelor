package cz.klecansky.projectmanagement.user.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.user.exception.ExpiredTokenException;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.entity.VerificationTokenEntity;
import cz.klecansky.projectmanagement.user.io.repository.UserRepository;
import cz.klecansky.projectmanagement.user.io.repository.VerificationTokenRepository;
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
public class VerificationTokenService {

    @NonNull VerificationTokenRepository verificationTokenRepository;
    @NonNull UserRepository userRepository;
    @NonNull UserMapper userMapper;

    public void saveVerificationToken(VerificationTokenEntity token) {
        verificationTokenRepository.save(token);
    }

    public Optional<VerificationTokenEntity> getToken(UUID token) {
        return verificationTokenRepository.findById(token);
    }

    public UserCommand verifyUserEmail(UUID token) {
        VerificationTokenEntity tokenEntity = getToken(token).orElseThrow(NoSuchElementFoundException::new);
        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            verificationTokenRepository.delete(tokenEntity);
            throw new ExpiredTokenException("Verification token is expired please request new one.");
        }
        UserEntity user = tokenEntity.getUser();
        user.setEmailVerified(true);
        verificationTokenRepository.delete(tokenEntity);
        return userMapper.userEntityToUserCommand(userRepository.save(user));
    }
}
