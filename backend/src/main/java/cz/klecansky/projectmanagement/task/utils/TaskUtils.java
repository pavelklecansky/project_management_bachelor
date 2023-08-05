package cz.klecansky.projectmanagement.task.utils;

import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskUtils {

    public static boolean assignedToTask(TaskCommand taskCommand, UserPrincipal userPrincipal) {
        return isAssigned(taskCommand, userPrincipal) || isAssignedForGroup(taskCommand, userPrincipal);
    }

    private static boolean isAssigned(TaskCommand taskCommand, UserPrincipal userPrincipal) {
        return !Objects.isNull(taskCommand.getAssigned())
                && taskCommand
                        .getAssigned()
                        .getId()
                        .equals(userPrincipal.getUser().getId());
    }

    private static boolean isAssignedForGroup(TaskCommand taskCommand, UserPrincipal userPrincipal) {
        return !Objects.isNull(taskCommand.getAssignedForGroup())
                && taskCommand.getAssignedForGroup().getMembers().stream().anyMatch(memberCommand -> memberCommand
                        .getUser()
                        .getId()
                        .equals(userPrincipal.getUser().getId()));
    }
}
