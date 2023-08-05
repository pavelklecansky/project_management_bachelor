package cz.klecansky.projectmanagement.project.service;

import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {

    ProjectCommand create(ProjectCommand projectCommand);

    List<ProjectCommand> getAll();

    Optional<ProjectCommand> get(UUID id) throws NoSuchElementFoundException;

    Optional<ProjectCommand> getByPhaseId(UUID id) throws NoSuchElementFoundException;

    Optional<ProjectCommand> getByTasksId(UUID id) throws NoSuchElementFoundException;

    void delete(UUID id);

    ProjectCommand update(UUID id, ProjectCommand projectCommand);

    ProjectCommand addComment(UUID id, CommentCommand commentCommand);

    ProjectCommand deleteComment(UUID id, UUID idComment);

    ProjectCommand addMember(UUID id, UserCommand userCommand);

    ProjectCommand deleteMember(UUID id, UUID memberId);

    ProjectCommand addGroupMember(UUID id, GroupCommand groupCommand);

    ProjectCommand deleteGroupMember(UUID id, UUID memberId);
}
