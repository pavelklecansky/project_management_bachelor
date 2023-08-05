package cz.klecansky.projectmanagement.user.ui.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewPasswordRequest {
    @NotBlank
    UUID token;

    @NotBlank
    String newPassword;
}
