package cz.klecansky.projectmanagement.group.shared;

import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.util.UUID;
import lombok.Data;

@Data
public class GroupMemberCommand {
    private UUID id;
    private UserCommand user;
    private String position;
}
