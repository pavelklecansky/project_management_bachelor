package cz.klecansky.projectmanagement.project.ui.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AddMemberRequest {
    UUID user;
}
