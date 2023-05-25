package cz.klecansky.projectmanagement.user.ui.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequestRequest {
    @Email
    @NotBlank
    String email;
}
