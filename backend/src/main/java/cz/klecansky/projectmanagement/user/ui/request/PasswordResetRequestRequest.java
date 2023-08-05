package cz.klecansky.projectmanagement.user.ui.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequestRequest {
    @Email
    @NotBlank
    String email;
}
