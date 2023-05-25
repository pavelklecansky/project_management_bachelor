package cz.klecansky.projectmanagement.group.service;

import cz.klecansky.projectmanagement.group.shared.GroupCommand;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {
    List<GroupCommand> getGroups();

    GroupCommand createGroup(GroupCommand groupCommand);

    Optional<GroupCommand> getGroup(UUID id);

    void deleteGroup(UUID id);

    GroupCommand updateGroup(UUID id, GroupCommand groupRequestToGroupCommand);
}
