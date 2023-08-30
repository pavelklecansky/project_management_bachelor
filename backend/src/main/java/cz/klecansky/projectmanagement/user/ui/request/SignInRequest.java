package cz.klecansky.projectmanagement.user.ui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class SignInRequest {
    @NotBlank
    String password;

    @Email
    @NotBlank
    String email;
}
