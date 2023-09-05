package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.budget.io.BudgetEntity;
import cz.klecansky.projectmanagement.budget.io.BudgetRepository;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.group.shared.mappers.GroupMapper;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
import cz.klecansky.projectmanagement.schedule.io.ScheduleEntity;
import cz.klecansky.projectmanagement.schedule.io.ScheduleRepository;
import cz.klecansky.projectmanagement.security.SecurityUtils;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
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
public class ProjectServiceImpl implements ProjectService {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    ProjectMapper projectMapper;

    @NonNull
    GroupMapper groupMapper;

    @NonNull
    UserMapper userMapper;

    @NonNull
    ScheduleRepository scheduleRepository;

    @NonNull
    BudgetRepository budgetRepository;

    @Override
    public ProjectCommand create(ProjectCommand projectCommand) {
        projectCommand.setId(UUID.randomUUID());
        ProjectEntity projectEntity = projectMapper.projectCommandToProjectEntity(projectCommand);
        projectEntity.setMembers(List.of(SecurityUtils.getCurrentUser().getUser()));
        ProjectEntity savedEntity = projectRepository.save(projectEntity);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(UUID.randomUUID());
        scheduleEntity.setProject(projectEntity);
        scheduleRepository.save(scheduleEntity);
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(UUID.randomUUID());
        budgetEntity.setProject(projectEntity);
        budgetRepository.save(budgetEntity);
        return projectMapper.projectEntityToProjectCommand(savedEntity);
    }

    @Override
    public List<ProjectCommand> getAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::projectEntityToProjectCommand)
                .toList();
    }

    @Override
    public Optional<ProjectCommand> get(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByPhaseId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByPhases_Id(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public Optional<ProjectCommand> getByTasksId(UUID id) throws NoSuchElementFoundException {
        Optional<ProjectEntity> projectEntity = projectRepository.findByTasks_Id(id);
        return projectEntity.map(projectMapper::projectEntityToProjectCommand);
    }

    @Override
    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectCommand update(UUID id, ProjectCommand projectCommand) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.setDescription(projectCommand.getDescription());
        projectEntity.setName(projectCommand.getName());
        projectEntity.setStartDate(projectCommand.getStartDate());
        projectEntity.setEndDate(projectCommand.getEndDate());
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    @Override
    public ProjectCommand addMember(UUID id, UserCommand userCommand) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        UserEntity userEntity = userMapper.userCommandToUserEntity(userCommand);
        if (projectEntity.getMembers().contains(userEntity)) {
            throw new IllegalArgumentException("Member is already in the project");
        } else {
            projectEntity.getMembers().add(userEntity);
        }
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    @Override
    public ProjectCommand deleteMember(UUID id, UUID memberId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getMembers().removeIf(user -> user.getId().equals(memberId));
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    @Override
    public ProjectCommand addGroupMember(UUID id, GroupCommand groupCommand) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        GroupEntity groupEntity = groupMapper.groupCommandToGroupEntity(groupCommand);
        if (projectEntity.getMemberGroups().contains(groupEntity)) {
            throw new IllegalArgumentException("Group member is already in the project");
        } else {
            projectEntity.getMemberGroups().add(groupEntity);
        }
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    @Override
    public ProjectCommand deleteGroupMember(UUID id, UUID memberId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getMemberGroups().removeIf(user -> user.getId().equals(memberId));
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }
}
