package cz.klecansky.projectmanagement.organization.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationService {

    OrganizationCommand create(OrganizationCommand organizationCommand);

    List<OrganizationCommand> getAll();

    Optional<OrganizationCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    OrganizationCommand update(UUID id, OrganizationCommand organizationCommand);
}
