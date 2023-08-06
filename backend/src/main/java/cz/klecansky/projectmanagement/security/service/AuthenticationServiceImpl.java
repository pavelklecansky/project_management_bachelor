package cz.klecansky.projectmanagement.security.service;

import cz.klecansky.projectmanagement.security.jwt.JWTToken;
import cz.klecansky.projectmanagement.security.jwt.TokenProvider;
import cz.klecansky.projectmanagement.user.io.entity.NewUserPasscodeEntity;
import cz.klecansky.projectmanagement.user.io.repository.NewUserPasscodeRepository;
import cz.klecansky.projectmanagement.user.service.EmailService;
import cz.klecansky.projectmanagement.user.service.PasswordResetTokenService;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.shared.NewUserPasscodeCommand;
import cz.klecansky.projectmanagement.user.shared.NewUserPasscodeMapper;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @NonNull
    AuthenticationManager authenticationManager;

    @NonNull
    TokenProvider tokenProvider;

    @NonNull
    UserService userService;

    @NonNull
    EmailService emailService;

    @NonNull
    NewUserPasscodeMapper newUserPasscodeMapper;

    @NonNull
    PasswordResetTokenService passwordResetTokenService;

    @NonNull
    NewUserPasscodeRepository newUserPasscodeRepository;

    @Override
    public JWTToken signIn(String username, String password) {
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.createToken(authenticate);
    }

    @Override
    public void forgottenPasswordRequest(String email) {
        UserCommand user = userService
                .getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        emailService.sendForgottenPasswordEmail(user);
    }

    @Override
    public boolean forgottenPasswordTokenCheck(UUID token) {
        return passwordResetTokenService.getToken(token).isPresent();
    }

    @Override
    public NewUserPasscodeCommand generateNewUserPasscode() {
        NewUserPasscodeEntity newUserPasscodeEntity = new NewUserPasscodeEntity();
        newUserPasscodeEntity.setId(UUID.randomUUID());
        newUserPasscodeEntity.setPasscode(ThreadLocalRandom.current().nextInt(1000000, 9999999 + 1));
        return newUserPasscodeMapper.newUserPasscodeEntityToNewUserPasscodeCommand(
                newUserPasscodeRepository.save(newUserPasscodeEntity));
    }
}
