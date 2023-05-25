package cz.klecansky.projectmanagement.group.ui.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GroupMemberRequest {
    private UUID id;
    private UUID user;
    private String position;
}
