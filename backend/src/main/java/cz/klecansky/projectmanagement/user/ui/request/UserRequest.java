package cz.klecansky.projectmanagement.user.ui.request;

import cz.klecansky.projectmanagement.user.shared.Role;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;


@Data
public class UserRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Nullable
    private String phoneNumber;

    @Nullable
    private String note;

    private List<UUID> organizations;

    private List<Role> roles;
}
