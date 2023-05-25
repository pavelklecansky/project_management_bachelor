package cz.klecansky.projectmanagement.group.ui.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GroupRequest {
    private UUID id;
    private String name;
    private List<GroupMemberRequest> members;
}
