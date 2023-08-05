package cz.klecansky.projectmanagement.phase.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.phase.io.PhaseRepository;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.phase.shared.PhaseMapper;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
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
public class PhaseServiceImpl implements PhaseService {

    @NonNull
    PhaseRepository phaseRepository;

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    PhaseMapper phaseMapper;

    @NonNull
    ProjectMapper projectMapper;

    @Override
    public PhaseCommand create(ProjectCommand projectCommand, PhaseCommand phaseCommand) {
        phaseCommand.setId(UUID.randomUUID());
        projectCommand.getPhases().add(phaseCommand);
        projectRepository.save(projectMapper.projectCommandToProjectEntity(projectCommand));
        return phaseCommand;
    }

    @Override
    public List<PhaseCommand> getAll() {
        return phaseRepository.findAll().stream()
                .map(phaseMapper::phaseEntityToPhaseCommand)
                .toList();
    }

    @Override
    public Optional<PhaseCommand> get(UUID id) throws NoSuchElementFoundException {
        return phaseRepository.findById(id).map(phaseMapper::phaseEntityToPhaseCommand);
    }

    @Override
    public void delete(UUID id) {
        phaseRepository.deleteById(id);
    }

    @Override
    public PhaseCommand update(UUID id, PhaseCommand phaseCommand) {
        PhaseEntity phaseEntity = phaseRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        phaseEntity.setName(phaseCommand.getName());
        phaseEntity.setNote(phaseCommand.getNote());
        phaseEntity.setStartDate(phaseCommand.getStartDate());
        phaseEntity.setEndDate(phaseCommand.getEndDate());
        return phaseMapper.phaseEntityToPhaseCommand(phaseRepository.save(phaseEntity));
    }
}
