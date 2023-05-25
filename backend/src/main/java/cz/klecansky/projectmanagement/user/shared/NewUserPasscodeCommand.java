package cz.klecansky.projectmanagement.user.shared;

import lombok.Data;

import java.util.UUID;

@Data
public class NewUserPasscodeCommand {
    private UUID id;
    private int passcode;
}
