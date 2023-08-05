package cz.klecansky.projectmanagement.group.ui.request;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GroupRequest {
    private UUID id;
    private String name;
    private List<GroupMemberRequest> members;
}
