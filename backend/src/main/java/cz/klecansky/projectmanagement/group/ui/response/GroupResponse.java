package cz.klecansky.projectmanagement.group.ui.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GroupResponse {
    private UUID id;
    private String name;
    private List<GroupMemberResponse> members;
}
