package cz.klecansky.projectmanagement.group.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.exceptions.GroupCannotBeDeletedException;
import cz.klecansky.projectmanagement.group.io.GroupRepository;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.group.shared.GroupMemberCommand;
import cz.klecansky.projectmanagement.group.shared.mappers.GroupMapper;
import cz.klecansky.projectmanagement.group.shared.mappers.GroupMemberMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    @NonNull
    GroupRepository groupRepository;

    @NonNull
    GroupMapper groupMapper;

    @NonNull
    GroupMemberMapper groupMemberMapper;

    @Override
    public List<GroupCommand> getGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::groupEntityToGroupCommand)
                .toList();
    }

    @Override
    public Optional<GroupCommand> getGroup(UUID id) {
        return groupRepository.findById(id).map(groupMapper::groupEntityToGroupCommand);
    }

    @Override
    public void deleteGroup(UUID id) {
        try {
            groupRepository.deleteById(id);
        } catch (Exception e) {
            throw new GroupCannotBeDeletedException("Group cannot be deleted.", e);
        }
    }

    @Override
    public GroupCommand updateGroup(UUID id, GroupCommand groupCommand) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        groupEntity.setName(groupCommand.getName());
        groupEntity.getMembers().clear();
        for (GroupMemberCommand member : groupCommand.getMembers()) {
            groupEntity.getMembers().add(groupMemberMapper.groupMemberCommandToGroupMemberEntity(member));
        }
        return groupMapper.groupEntityToGroupCommand(groupRepository.save(groupEntity));
    }

    @Override
    public GroupCommand createGroup(GroupCommand groupCommand) {
        groupCommand.setId(UUID.randomUUID());
        for (GroupMemberCommand member : groupCommand.getMembers()) {
            member.setId(UUID.randomUUID());
        }
        return groupMapper.groupEntityToGroupCommand(
                groupRepository.save(groupMapper.groupCommandToGroupEntity(groupCommand)));
    }
}
