package cz.klecansky.projectmanagement.user.ui.response;

import lombok.Data;

import java.util.UUID;

@Data
public class NewUserPasscodeResponse {
    private UUID id;
    private int passcode;
}
