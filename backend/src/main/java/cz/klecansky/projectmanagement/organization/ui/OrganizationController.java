package cz.klecansky.projectmanagement.organization.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.ORGANIZATIONS_API;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.organization.service.OrganizationService;
import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import cz.klecansky.projectmanagement.organization.shared.OrganizationMapper;
import cz.klecansky.projectmanagement.organization.ui.request.OrganizationRequest;
import cz.klecansky.projectmanagement.organization.ui.response.OrganizationResponse;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(ORGANIZATIONS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrganizationController {

    @NonNull
    OrganizationService organizationService;

    @NonNull
    OrganizationMapper organizationMapper;

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrganization(@PathVariable UUID id) throws NoSuchElementFoundException {
        return organizationService
                .get(id)
                .map(organizationCommand -> ResponseEntity.ok(
                        organizationMapper.organizationCommandToOrganizationResponse(organizationCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrganizations() {
        List<OrganizationResponse> organizationRespons = organizationService.getAll().stream()
                .map(organizationMapper::organizationCommandToOrganizationResponse)
                .toList();
        return ResponseEntity.ok(organizationRespons);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> createOrganization(@RequestBody OrganizationRequest request) {
        OrganizationCommand createdOrganization =
                organizationService.create(organizationMapper.organizationRequestToOrganizationCommand(request));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/organizations/{id}")
                .buildAndExpand(createdOrganization.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(SuccessResponse.builder()
                        .message("Organiation was successfully created.")
                        .data(organizationMapper.organizationCommandToOrganizationResponse(createdOrganization))
                        .build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteOrganization(@PathVariable UUID id) {
        organizationService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Organization was successfully deleted.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> updateOrganization(
            @PathVariable UUID id, @RequestBody OrganizationRequest request) {
        OrganizationCommand update =
                organizationService.update(id, organizationMapper.organizationRequestToOrganizationCommand(request));
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Organization was successfully updated")
                .data(organizationMapper.organizationCommandToOrganizationResponse(update))
                .build());
    }
}
