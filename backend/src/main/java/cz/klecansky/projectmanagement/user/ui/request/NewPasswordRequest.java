package cz.klecansky.projectmanagement.user.ui.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class NewPasswordRequest {
    @NotBlank
    UUID token;
    @NotBlank
    String newPassword;
}
