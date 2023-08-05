package cz.klecansky.projectmanagement.organization.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class OrganizationResponse {
    private UUID id;

    private String name;

    private String email;

    private String ico;

    private String phoneNumber;

    private String note;
}
