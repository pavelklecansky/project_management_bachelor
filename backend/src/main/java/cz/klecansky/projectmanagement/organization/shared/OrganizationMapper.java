package cz.klecansky.projectmanagement.organization.shared;

import cz.klecansky.projectmanagement.organization.io.OrganizationEntity;
import cz.klecansky.projectmanagement.organization.ui.request.OrganizationRequest;
import cz.klecansky.projectmanagement.organization.ui.response.OrganizationResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrganizationMapper {

    @NonNull
    ModelMapper modelMapper;

    public OrganizationEntity organizationCommandToOrganizationEntity(OrganizationCommand organizationCommand) {
        return modelMapper.map(organizationCommand, OrganizationEntity.class);
    }

    public OrganizationCommand organizationEntityToOrganizationCommand(OrganizationEntity organizationEntity) {
        return modelMapper.map(organizationEntity, OrganizationCommand.class);
    }

    public OrganizationCommand organizationRequestToOrganizationCommand(OrganizationRequest organizationRequest) {
        return modelMapper.map(organizationRequest, OrganizationCommand.class);
    }

    public OrganizationResponse organizationCommandToOrganizationResponse(OrganizationCommand organizationCommand) {
        return modelMapper.map(organizationCommand, OrganizationResponse.class);
    }
}
