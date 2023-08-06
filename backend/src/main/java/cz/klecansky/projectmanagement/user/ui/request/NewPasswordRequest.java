package cz.klecansky.projectmanagement.user.ui.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Data;

@Data
public class NewPasswordRequest {
    @NotBlank
    UUID token;

    @NotBlank
    String newPassword;
}
