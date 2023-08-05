package cz.klecansky.projectmanagement.project.ui.request;

import java.util.UUID;
import lombok.Data;

@Data
public class AddGroupMemberRequest {
    UUID group;
}
