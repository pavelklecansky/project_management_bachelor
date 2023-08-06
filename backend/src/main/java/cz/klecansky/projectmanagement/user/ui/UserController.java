package cz.klecansky.projectmanagement.user.ui;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.user.exception.UserDeletionException;
import cz.klecansky.projectmanagement.user.service.UserService;
import cz.klecansky.projectmanagement.user.service.VerificationTokenService;
import cz.klecansky.projectmanagement.user.shared.Role;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import cz.klecansky.projectmanagement.user.ui.request.ChangePasswordRequest;
import cz.klecansky.projectmanagement.user.ui.request.NewPasswordRequest;
import cz.klecansky.projectmanagement.user.ui.request.SignUpRequest;
import cz.klecansky.projectmanagement.user.ui.request.UserRequest;
import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static cz.klecansky.projectmanagement.core.WebConstants.USERS_API;

@RestController
@RequestMapping(USERS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {

    @NonNull
    UserMapper userMapper;

    @NonNull
    Converter<UUID, OrganizationCommand> UUIDToOrganizationCommandConverter;

    @NonNull
    UserService userService;

    @NonNull
    VerificationTokenService verificationTokenService;





    @PostMapping("register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid SignUpRequest userRequest) {
        userRequest.setRoles(List.of(Role.ROLE_USER));
        UserCommand createdUser =
                userService.createUser(userMapper.userRequestToUserCommand(userRequest), userRequest.getPasscode());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("User was successfully registered.")
                        .data(userMapper.userCommandToUserResponse(createdUser))
                        .build());
    }

    @GetMapping(value = "currentUser")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> currentUserName(
            @CurrentSecurityContext(expression = "authentication.name") String email) {
        UserCommand currentUser = userService
                .getUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementFoundException("User is not logged."));
        return ResponseEntity.ok(userMapper.userCommandToUserResponse(currentUser));
    }




    @PostMapping("newPassword")
    public ResponseEntity<SuccessResponse<UserResponse>> newPassword(@RequestBody NewPasswordRequest request) {
        UserCommand userCommand = userService.newPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(SuccessResponse.<UserResponse>builder()
                .message("Password successfully changed.")
                .data(userMapper.userCommandToUserResponse(userCommand))
                .build());
    }

    @PostMapping("changePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<UserResponse>> changePassword(
            @RequestBody ChangePasswordRequest request,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        UserCommand userCommand =
                userService.changePassword(userPrincipal, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok(SuccessResponse.<UserResponse>builder()
                .message("Password successfully changed.")
                .data(userMapper.userCommandToUserResponse(userCommand))
                .build());
    }

    @GetMapping("register/token/{id}")
    public ResponseEntity<UserResponse> verifyEmail(@PathVariable UUID id) {
        UserCommand userCommand = verificationTokenService.verifyUserEmail(id);
        return ResponseEntity.ok(userMapper.userCommandToUserResponse(userCommand));
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) throws NoSuchElementFoundException {
        return userService
                .getUser(id)
                .map(projectCommand -> ResponseEntity.ok(userMapper.userCommandToUserResponse(projectCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> projectResponses = userService.getUsers().stream()
                .map(userMapper::userCommandToUserResponse)
                .toList();
        return ResponseEntity.ok(projectResponses);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<Object>> deleteUser(
            @PathVariable UUID id,
            @CurrentSecurityContext(expression = "authentication.principal") UserPrincipal userPrincipal) {
        if (userPrincipal.getUser().getId().equals(id)) {
            throw new UserDeletionException("User cannot delete yourself.");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("User was successfully deleted")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN') or authentication.principal.user.id == #id")
    public ResponseEntity<SuccessResponse<UserResponse>> updateUser(
            @PathVariable UUID id, @Valid @RequestBody UserRequest request) {
        UserCommand userCommand = userMapper.userRequestToUserCommand(request);
        userCommand.setOrganizations(request.getOrganizations().stream()
                .map(UUIDToOrganizationCommandConverter::convert)
                .toList());
        UserCommand update = userService.updateUser(id, userCommand);
        return ResponseEntity.ok(SuccessResponse.<UserResponse>builder()
                .message("User was successfully edit.")
                .data(userMapper.userCommandToUserResponse(update))
                .build());
    }


}
