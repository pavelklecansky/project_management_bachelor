package cz.klecansky.projectmanagement.user.shared;

import cz.klecansky.projectmanagement.user.io.entity.NewUserPasscodeEntity;
import cz.klecansky.projectmanagement.user.ui.response.NewUserPasscodeResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NewUserPasscodeMapper {

    @NonNull
    ModelMapper modelMapper;

    public NewUserPasscodeResponse newUserPasscodeCommandToNewUserPasscodeResponse(
            NewUserPasscodeCommand newUserPasscodeCommand) {
        return modelMapper.map(newUserPasscodeCommand, NewUserPasscodeResponse.class);
    }

    public NewUserPasscodeEntity newUserPasscodeCommandToNewUserPasscodeEntity(
            NewUserPasscodeCommand newUserPasscodeCommand) {
        return modelMapper.map(newUserPasscodeCommand, NewUserPasscodeEntity.class);
    }

    public NewUserPasscodeCommand newUserPasscodeEntityToNewUserPasscodeCommand(
            NewUserPasscodeEntity newUserPasscodeEntity) {
        return modelMapper.map(newUserPasscodeEntity, NewUserPasscodeCommand.class);
    }
}
