package cz.klecansky.projectmanagement.user.ui.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank
    String currentPassword;
    @NotBlank
    String newPassword;
}
