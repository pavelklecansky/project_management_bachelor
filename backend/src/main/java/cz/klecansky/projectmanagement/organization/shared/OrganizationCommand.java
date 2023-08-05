package cz.klecansky.projectmanagement.organization.shared;

import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
public class OrganizationCommand {

    private UUID id;

    private String name;

    private String email;

    private String ico;

    private String phoneNumber;

    private String note;

    @ToString.Exclude
    private List<UserCommand> users;
}
