package cz.klecansky.projectmanagement.user.shared;

import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.ui.request.SignUpRequest;
import cz.klecansky.projectmanagement.user.ui.request.UserRequest;
import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserMapper {

    @NonNull
    ModelMapper modelMapper;

    public UserCommand userRequestToUserCommand(UserRequest userRequests) {
        return modelMapper.map(userRequests, UserCommand.class);
    }

    public UserCommand userRequestToUserCommand(SignUpRequest signUpRequest) {
        return modelMapper.map(signUpRequest, UserCommand.class);
    }

    public UserResponse userCommandToUserResponse(UserCommand userCommand) {
        return modelMapper.map(userCommand, UserResponse.class);
    }

    public UserEntity userCommandToUserEntity(UserCommand userCommand) {
        return modelMapper.map(userCommand, UserEntity.class);
    }

    public UserCommand userEntityToUserCommand(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserCommand.class);
    }
}
