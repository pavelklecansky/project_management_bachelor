package cz.klecansky.projectmanagement.organization.ui.request;

import lombok.Data;

@Data
public class OrganizationRequest {

    private String name;

    private String email;

    private String ico;

    private String phoneNumber;

    private String note;
}
