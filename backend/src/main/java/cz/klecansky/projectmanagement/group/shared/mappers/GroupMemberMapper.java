package cz.klecansky.projectmanagement.group.shared.mappers;

import cz.klecansky.projectmanagement.group.io.entity.GroupMemberEntity;
import cz.klecansky.projectmanagement.group.shared.GroupMemberCommand;
import cz.klecansky.projectmanagement.group.ui.request.GroupMemberRequest;
import cz.klecansky.projectmanagement.group.ui.response.GroupMemberResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GroupMemberMapper {

    @NonNull
    ModelMapper modelMapper;

    public GroupMemberCommand groupMemberRequestToGroupMemberCommand(GroupMemberRequest groupMemberRequest) {
        return modelMapper.map(groupMemberRequest, GroupMemberCommand.class);
    }

    public GroupMemberResponse groupMemberCommandToGroupMemberResponse(GroupMemberCommand groupMemberCommand) {
        return modelMapper.map(groupMemberCommand, GroupMemberResponse.class);
    }

    public GroupMemberEntity groupMemberCommandToGroupMemberEntity(GroupMemberCommand groupMemberCommand) {
        return modelMapper.map(groupMemberCommand, GroupMemberEntity.class);
    }

    public GroupMemberCommand userEntityToUserCommand(GroupMemberEntity groupMemberEntity) {
        return modelMapper.map(groupMemberEntity, GroupMemberCommand.class);
    }
}
