package cz.klecansky.projectmanagement.group.ui.response;

import cz.klecansky.projectmanagement.user.ui.response.UserResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class GroupMemberResponse {
    private UUID id;
    private UserResponse user;
    private String position;
}
