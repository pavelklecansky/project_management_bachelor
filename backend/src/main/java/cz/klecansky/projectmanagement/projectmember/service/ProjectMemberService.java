package cz.klecansky.projectmanagement.projectmember.service;

import cz.klecansky.projectmanagement.core.ByIdLoader;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.io.ProjectRepository;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectMapper;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectMemberService {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    ProjectMapper projectMapper;

    @NonNull
    ByIdLoader<UUID, UserEntity> userLoader;

    @NonNull
    ByIdLoader<UUID, GroupEntity> groupLoader;

    public ProjectCommand addMember(UUID id, UUID userId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        UserEntity userEntity = userLoader.getById(userId);
        if (projectEntity.getMembers().contains(userEntity)) {
            throw new IllegalArgumentException("Member is already in the project");
        } else {
            projectEntity.getMembers().add(userEntity);
        }
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public ProjectCommand deleteMember(UUID id, UUID memberId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getMembers().removeIf(user -> user.getId().equals(memberId));
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public ProjectCommand addGroupMember(UUID id, UUID groupId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        GroupEntity groupEntity = groupLoader.getById(groupId);
        if (projectEntity.getMemberGroups().contains(groupEntity)) {
            throw new IllegalArgumentException("Group member is already in the project");
        } else {
            projectEntity.getMemberGroups().add(groupEntity);
        }
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }

    public ProjectCommand deleteGroupMember(UUID id, UUID memberId) {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(NoSuchElementFoundException::new);
        projectEntity.getMemberGroups().removeIf(user -> user.getId().equals(memberId));
        return projectMapper.projectEntityToProjectCommand(projectRepository.save(projectEntity));
    }
}
