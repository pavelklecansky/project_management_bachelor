package cz.klecansky.projectmanagement.security.service;

import cz.klecansky.projectmanagement.security.jwt.JWTToken;
import cz.klecansky.projectmanagement.user.shared.NewUserPasscodeCommand;

import java.util.UUID;

public interface AuthenticationService {
    JWTToken signIn(String username, String password);

    void forgottenPasswordRequest(String username);

    boolean forgottenPasswordTokenCheck(UUID token);

    NewUserPasscodeCommand generateNewUserPasscode();

}
