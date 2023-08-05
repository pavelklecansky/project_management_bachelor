package cz.klecansky.projectmanagement.user.ui.request;

import cz.klecansky.projectmanagement.user.shared.Role;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @NotBlank
    private String passcode;

    @Email
    @NotBlank
    private String email;

    private List<Role> roles;
}
