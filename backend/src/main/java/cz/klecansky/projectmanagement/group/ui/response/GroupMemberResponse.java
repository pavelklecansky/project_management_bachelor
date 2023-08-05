package cz.klecansky.projectmanagement.group.ui.response;

import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import java.util.UUID;
import lombok.Data;

@Data
public class GroupMemberResponse {
    private UUID id;
    private UserResponse user;
    private String position;
}
