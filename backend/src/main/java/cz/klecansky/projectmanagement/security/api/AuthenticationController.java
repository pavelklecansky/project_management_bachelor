package cz.klecansky.projectmanagement.security.api;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.security.jwt.JWTToken;
import cz.klecansky.projectmanagement.security.service.AuthenticationService;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.shared.NewUserPasscodeCommand;
import cz.klecansky.projectmanagement.user.shared.NewUserPasscodeMapper;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import cz.klecansky.projectmanagement.user.ui.request.PasswordResetCheckRequest;
import cz.klecansky.projectmanagement.user.ui.request.PasswordResetRequestRequest;
import cz.klecansky.projectmanagement.user.ui.request.SignInRequest;
import cz.klecansky.projectmanagement.user.ui.response.NewUserPasscodeResponse;
import cz.klecansky.projectmanagement.user.ui.response.SignInResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.klecansky.projectmanagement.core.WebConstants.USERS_API;

@RestController
@RequestMapping(USERS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticationController {

    @NonNull
    UserService userService;

    @NonNull
    AuthenticationService authenticationService;


    @NonNull
    UserMapper userMapper;

    @NonNull
    NewUserPasscodeMapper newUserPasscodeMapper;


    @PostMapping(path = "login")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        JWTToken signin = authenticationService.signIn(signInRequest.getEmail(), signInRequest.getPassword());
        UserCommand currentUser = userService
                .getUserByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new NoSuchElementFoundException("User is not logged."));
        return ResponseEntity.ok(new SignInResponse(signin, userMapper.userCommandToUserResponse(currentUser)));
    }

    @PostMapping("passwordReset")
    public ResponseEntity<SuccessResponse> passwordResetRequest(
            @RequestBody @Valid PasswordResetRequestRequest passwordResetRequestRequest) {
        authenticationService.forgottenPasswordRequest(passwordResetRequestRequest.getEmail());
        return ResponseEntity.ok(SuccessResponse.builder()
                                         .message("Password reset request was send to you email.")
                                         .build());
    }

    @PostMapping("passwordReset/check")
    public ResponseEntity<Boolean> passwordResetCheck(@RequestBody PasswordResetCheckRequest request) {
        return ResponseEntity.ok(authenticationService.forgottenPasswordTokenCheck(request.getId()));
    }


    @PostMapping("generatePasscode")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<NewUserPasscodeResponse>> generatePasscode() {
        NewUserPasscodeCommand newUserPasscodeCommand = authenticationService.generateNewUserPasscode();
        return ResponseEntity.ok(SuccessResponse.<NewUserPasscodeResponse>builder()
                                         .message("Passcode was generated")
                                         .data(newUserPasscodeMapper.newUserPasscodeCommandToNewUserPasscodeResponse(newUserPasscodeCommand))
                                         .build());
    }
}
