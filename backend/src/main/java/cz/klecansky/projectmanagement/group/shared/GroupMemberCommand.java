package cz.klecansky.projectmanagement.group.shared;

import cz.klecansky.projectmanagement.user.shared.UserCommand;
import lombok.Data;

import java.util.UUID;

@Data
public class GroupMemberCommand {
    private UUID id;
    private UserCommand user;
    private String position;
}
