package cz.klecansky.projectmanagement.group.ui.request;

import java.util.UUID;
import lombok.Data;

@Data
public class GroupMemberRequest {
    private UUID id;
    private UUID user;
    private String position;
}
