package cz.klecansky.projectmanagement.user.shared;

import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
public class UserCommand implements Serializable {
    private static final long serialVersionUID = 684519260438364280L;

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerified;
    private String phoneNumber;
    private String note;

    @ToString.Exclude
    private List<OrganizationCommand> organizations;

    @ToString.Exclude
    private List<TaskCommand> tasks;

    private List<Role> roles;
}
