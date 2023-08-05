package cz.klecansky.projectmanagement.user.ui.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank
    String currentPassword;

    @NotBlank
    String newPassword;
}
