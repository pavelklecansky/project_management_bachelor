package cz.klecansky.projectmanagement.user.ui.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
}
