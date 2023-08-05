package cz.klecansky.projectmanagement.user.shared;

import java.util.UUID;
import lombok.Data;

@Data
public class NewUserPasscodeCommand {
    private UUID id;
    private int passcode;
}
