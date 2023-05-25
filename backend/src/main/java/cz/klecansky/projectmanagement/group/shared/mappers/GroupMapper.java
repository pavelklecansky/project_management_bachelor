package cz.klecansky.projectmanagement.group.shared.mappers;

import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.group.ui.request.GroupRequest;
import cz.klecansky.projectmanagement.group.ui.response.GroupResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GroupMapper {

    @NonNull ModelMapper modelMapper;

    public GroupCommand groupRequestToGroupCommand(GroupRequest groupRequest) {
        return modelMapper.map(groupRequest, GroupCommand.class);
    }

    public GroupResponse groupCommandToGroupResponse(GroupCommand groupCommand) {
        return modelMapper.map(groupCommand, GroupResponse.class);
    }

    public GroupEntity groupCommandToGroupEntity(GroupCommand groupCommand) {
        return modelMapper.map(groupCommand, GroupEntity.class);
    }

    public GroupCommand groupEntityToGroupCommand(GroupEntity groupEntity) {
        return modelMapper.map(groupEntity, GroupCommand.class);
    }
}
