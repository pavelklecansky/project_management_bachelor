package cz.klecansky.projectmanagement.organization.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.organization.io.OrganizationEntity;
import cz.klecansky.projectmanagement.organization.io.OrganizationRepository;
import cz.klecansky.projectmanagement.organization.shared.OrganizationCommand;
import cz.klecansky.projectmanagement.organization.shared.OrganizationMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    @NonNull
    OrganizationRepository organizationRepository;

    @NonNull
    OrganizationMapper organizationMapper;

    @Override
    public OrganizationCommand create(OrganizationCommand organizationCommand) {
        organizationCommand.setId(UUID.randomUUID());
        OrganizationEntity savedEntity = organizationRepository.save(
                organizationMapper.organizationCommandToOrganizationEntity(organizationCommand));
        return organizationMapper.organizationEntityToOrganizationCommand(savedEntity);
    }

    @Override
    public List<OrganizationCommand> getAll() {
        return organizationRepository.findAll().stream()
                .map(organizationMapper::organizationEntityToOrganizationCommand)
                .toList();
    }

    @Override
    public Optional<OrganizationCommand> get(UUID id) throws NoSuchElementFoundException {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        return organizationEntity.map(organizationMapper::organizationEntityToOrganizationCommand);
    }

    @Override
    public void delete(UUID id) {
        organizationRepository.deleteById(id);
    }

    @Override
    public OrganizationCommand update(UUID id, OrganizationCommand organizationCommand) {
        OrganizationEntity organizationEntity =
                organizationRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        organizationEntity.setEmail(organizationCommand.getEmail());
        organizationEntity.setName(organizationCommand.getName());
        organizationEntity.setIco(organizationCommand.getIco());
        organizationEntity.setPhoneNumber(organizationCommand.getPhoneNumber());
        organizationEntity.setNote(organizationCommand.getNote());
        return organizationMapper.organizationEntityToOrganizationCommand(
                organizationRepository.save(organizationEntity));
    }
}
