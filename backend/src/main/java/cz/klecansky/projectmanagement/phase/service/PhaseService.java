package cz.klecansky.projectmanagement.phase.service;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PhaseService {

    PhaseCommand create(ProjectCommand projectCommand, PhaseCommand phaseCommand);

    List<PhaseCommand> getAll();

    Optional<PhaseCommand> get(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    PhaseCommand update(UUID id, PhaseCommand phaseCommand);
}
