package cz.klecansky.projectmanagement.user.ui.response;

import cz.klecansky.projectmanagement.organization.ui.response.OrganizationResponse;
import cz.klecansky.projectmanagement.user.shared.Role;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String note;
    private Boolean emailVerified;
    private List<OrganizationResponse> organizations;
    private List<Role> roles;
}
