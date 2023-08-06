package cz.klecansky.projectmanagement.user.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.organization.shared.OrganizationMapper;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.user.exception.BadPasscodeException;
import cz.klecansky.projectmanagement.user.exception.EmailAlreadyExistsException;
import cz.klecansky.projectmanagement.user.exception.UserDeletionException;
import cz.klecansky.projectmanagement.user.io.entity.NewUserPasscodeEntity;
import cz.klecansky.projectmanagement.user.io.entity.PasswordResetTokenEntity;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.repository.NewUserPasscodeRepository;
import cz.klecansky.projectmanagement.user.io.repository.UserRepository;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static cz.klecansky.projectmanagement.security.SecurityUtils.*;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @NonNull
    UserRepository userRepository;

    @NonNull
    UserMapper userMapper;

    @NonNull
    OrganizationMapper organizationMapper;

    @NonNull
    PasswordEncoder passwordEncoder;


    @NonNull
    EmailService emailService;

    @NonNull
    NewUserPasscodeRepository newUserPasscodeRepository;


    @NonNull
    PasswordResetTokenService passwordResetTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findUserEntityByEmail(email);
        return new UserPrincipal(user.orElseThrow(() -> new UsernameNotFoundException("Username not found.")));
    }

    @Override
    public UserCommand createUser(UserCommand userCommand, String passcode) {
        if (userRepository.findUserEntityByEmail(userCommand.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already in use");
        }
        try {
            int passcodeNumber = Integer.parseInt(passcode);
            Optional<NewUserPasscodeEntity> newUserPasscodeEntityByPasscode =
                    newUserPasscodeRepository.findNewUserPasscodeEntityByPasscode(passcodeNumber);
            if (newUserPasscodeEntityByPasscode.isPresent()) {
                NewUserPasscodeEntity newUserPasscodeEntity = newUserPasscodeEntityByPasscode.get();
                newUserPasscodeRepository.delete(newUserPasscodeEntity);
            } else {
                throw new BadPasscodeException("Bad passcode provided");
            }
        } catch (Exception e) {
            throw new BadPasscodeException("Bad passcode provided");
        }
        userCommand.setId(UUID.randomUUID());
        UserEntity userEntity = userMapper.userCommandToUserEntity(userCommand);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userCommand.getPassword()));
        UserCommand savedUser = userMapper.userEntityToUserCommand(userRepository.save(userEntity));
        emailService.sendVerificationEmail(savedUser);
        return savedUser;
    }

    @Override
    public Optional<UserCommand> getUser(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(userMapper::userEntityToUserCommand);
    }

    @Override
    public Optional<UserCommand> getUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(email);
        return userEntity.map(userMapper::userEntityToUserCommand);
    }

    @Override
    public UserCommand updateUser(UUID userId, UserCommand user) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + userId + "' not found"));
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setNote(user.getNote());
        userEntity.setOrganizations(user.getOrganizations().stream()
                                            .map(organizationMapper::organizationCommandToOrganizationEntity)
                                            .collect(Collectors.toList()));
        if (loginUserIsSuperAdmin()) {
            userEntity.setRoles(user.getRoles());
        }
        UserEntity updatedUser = userRepository.save(userEntity);
        return userMapper.userEntityToUserCommand(updatedUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        Optional<UserCommand> user = getUser(userId);
        if (user.isPresent()) {
            UserDetails loginUser = (UserDetails)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!isSuperAdmin(loginUser)
                    && isAdmin(loginUser)
                    && isAdmin(user.get().getRoles())) {
                throw new UserDeletionException("Admin cannot delete other admin.");
            }
            userRepository.deleteById(userId);
        }
    }

    @Override
    public List<UserCommand> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userEntityToUserCommand)
                .toList();
    }

    @Override
    public UserCommand newPassword(UUID passwordResetToken, String newPassword) {
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenService
                .getToken(passwordResetToken)
                .orElseThrow(() -> new NoSuchElementFoundException("Password Reset Token was not found."));
        UserEntity user = passwordResetTokenEntity.getUser();
        user.setEncryptedPassword(passwordEncoder.encode(newPassword));
        UserEntity save = userRepository.save(user);
        return userMapper.userEntityToUserCommand(save);
    }

    @Override
    public UserCommand changePassword(UserPrincipal user, String currentPassword, String newPassword) {
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            UserEntity userEntity = user.getUser();
            userEntity.setEncryptedPassword(passwordEncoder.encode(newPassword));
            UserEntity save = userRepository.save(userEntity);
            return userMapper.userEntityToUserCommand(save);
        }
        throw new UsernameNotFoundException("Username not found.");
    }
}
