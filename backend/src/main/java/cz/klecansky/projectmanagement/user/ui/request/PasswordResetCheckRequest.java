package cz.klecansky.projectmanagement.user.ui.request;

import lombok.Data;

import java.util.UUID;

@Data
public class PasswordResetCheckRequest {
    UUID id;
}
