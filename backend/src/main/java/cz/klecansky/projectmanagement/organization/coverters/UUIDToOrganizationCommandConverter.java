package cz.klecansky.projectmanagement.organization.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.organization.service.OrganizationService;
import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class UUIDToOrganizationCommandConverter implements Converter<UUID, OrganizationCommand> {

    @NonNull
    OrganizationService organizationService;

    @Override
    public OrganizationCommand convert(@NonNull UUID source) {
        return organizationService
                .get(source)
                .orElseThrow(() -> new NoSuchElementFoundException("Organization was not found."));
    }
}
