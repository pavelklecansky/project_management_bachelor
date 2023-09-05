package cz.klecansky.projectmanagement.outcome.service;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryRepository;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryOldMapper;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryUpsertCommand;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
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
public class OutcomeCategoryServiceImpl implements OutcomeCategoryService {

    @NonNull
    OutcomeCategoryRepository outcomeCategoryRepository;

    @NonNull
    OutcomeCategoryOldMapper outcomeCategoryOldMapper;

    @NonNull
    ProjectService projectService;

    @NonNull
    ByIdLoader<UUID, ProjectEntity> projectLoader;

    @Override
    public List<OutcomeCategoryCommand> getOutcomesCategoryByProjectId(UUID id) {
        ProjectCommand projectCommand =
                projectService.get(id).orElseThrow(() -> new NoSuchElementFoundException("Project was not found"));
        return projectCommand.getOutcomeCategories();
    }

    @Override
    public OutcomeCategoryCommand upsert(OutcomeCategoryUpsertCommand command) {
        OutcomeCategoryEntity save = outcomeCategoryRepository.save(upsertOutcomeCategory(command));
        return outcomeCategoryOldMapper.outcomeCategoryEntityToOutcomeCategoryCommand(save);
    }

    @Override
    public Optional<OutcomeCategoryCommand> get(UUID id) throws NoSuchElementFoundException {
        return outcomeCategoryRepository
                .findById(id)
                .map(outcomeCategoryOldMapper::outcomeCategoryEntityToOutcomeCategoryCommand);
    }

    @Override
    public void delete(UUID id) {
        outcomeCategoryRepository.deleteById(id);
    }

    @Override
    public OutcomeCategoryCommand update(UUID id, OutcomeCategoryCommand outcomeCommand) {
        OutcomeCategoryEntity outcomeCategoryEntity =
                outcomeCategoryRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        outcomeCategoryEntity.setName(outcomeCommand.getName());
        outcomeCategoryEntity.setDescription(outcomeCommand.getDescription());
        return outcomeCategoryOldMapper.outcomeCategoryEntityToOutcomeCategoryCommand(
                outcomeCategoryRepository.save(outcomeCategoryEntity));
    }

    private OutcomeCategoryEntity upsertOutcomeCategory(OutcomeCategoryUpsertCommand command) {
        if (command.id() == null) {
            return createNewOutcome(command);
        }
        return updateExistingOutcome(command);
    }

    private OutcomeCategoryEntity updateExistingOutcome(OutcomeCategoryUpsertCommand command) {
        OutcomeCategoryEntity outcomeCategory = outcomeCategoryRepository
                .findById(command.id())
                .orElseThrow(() -> new NoSuchElementFoundException("Cannot update non existing outcome category"));

        if (command.name() != null) {
            outcomeCategory = outcomeCategory.withName(command.name());
        }
        if (command.description() != null) {
            outcomeCategory = outcomeCategory.withDescription(command.description());
        }
        if (command.projectId() != null) {
            ProjectEntity project = projectLoader.getById(command.projectId());
            outcomeCategory = outcomeCategory.withProject(project);
        }
        return outcomeCategory;
    }

    private OutcomeCategoryEntity createNewOutcome(OutcomeCategoryUpsertCommand command) {
        ProjectEntity project = projectLoader.getById(command.projectId());
        return new OutcomeCategoryEntity(UUID.randomUUID(), command.name(), command.description(), project, List.of());
    }
}
