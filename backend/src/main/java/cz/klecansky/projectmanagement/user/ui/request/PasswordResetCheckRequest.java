package cz.klecansky.projectmanagement.user.ui.request;

import java.util.UUID;
import lombok.Data;

@Data
public class PasswordResetCheckRequest {
    UUID id;
}
