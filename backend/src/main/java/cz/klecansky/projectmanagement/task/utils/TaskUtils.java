package cz.klecansky.projectmanagement.task.utils;

import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import java.util.Objects;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskUtils {

    public static boolean isAssignedToTask(TaskCommand taskCommand, UUID userId) {
        return isAssigned(taskCommand, userId) || isAssignedForGroup(taskCommand, userId);
    }

    private static boolean isAssigned(TaskCommand taskCommand, UUID userId) {
        return !Objects.isNull(taskCommand.getAssigned())
                && taskCommand.getAssigned().getId().equals(userId);
    }

    private static boolean isAssignedForGroup(TaskCommand taskCommand, UUID userId) {
        return !Objects.isNull(taskCommand.getAssignedForGroup())
                && taskCommand.getAssignedForGroup().getMembers().stream()
                        .anyMatch(
                                memberCommand -> memberCommand.getUser().getId().equals(userId));
    }
}
