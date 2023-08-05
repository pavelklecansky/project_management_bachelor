package cz.klecansky.projectmanagement.group.ui.response;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GroupResponse {
    private UUID id;
    private String name;
    private List<GroupMemberResponse> members;
}
