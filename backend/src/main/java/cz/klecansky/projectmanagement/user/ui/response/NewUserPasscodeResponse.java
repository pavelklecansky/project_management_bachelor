package cz.klecansky.projectmanagement.user.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class NewUserPasscodeResponse {
    private UUID id;
    private int passcode;
}
