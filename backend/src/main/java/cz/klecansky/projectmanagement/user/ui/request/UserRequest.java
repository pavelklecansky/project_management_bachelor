package cz.klecansky.projectmanagement.user.ui.request;

import cz.klecansky.projectmanagement.user.shared.Role;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.lang.Nullable;

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
