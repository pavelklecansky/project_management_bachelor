package cz.klecansky.projectmanagement.user.ui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;
}
