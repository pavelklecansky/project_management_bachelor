package cz.klecansky.projectmanagement.task.service;

import com.github.kagkarlsson.scheduler.SchedulerClient;
import com.github.kagkarlsson.scheduler.task.Task;
import cz.klecansky.projectmanagement.task.shared.GroupAssignToTaskCommand;
import cz.klecansky.projectmanagement.task.shared.UserAssignToTaskCommand;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TaskNotifier {

    @NonNull
    Task<UserAssignToTaskCommand> notifyUserAssignToTask;

    @NonNull
    Task<GroupAssignToTaskCommand> notifyGroupAssignToTask;

    @NonNull
    SchedulerClient scheduler;

    public void notifyUserAssignedToTask(UUID taskId, String taskName, String email) {
        scheduler.schedule(notifyUserAssignToTask.schedulableInstance(
                UUID.randomUUID().toString(), new UserAssignToTaskCommand(taskId, taskName, email)));
    }

    public void notifyGroupAssignedToTask(List<UserAssignToTaskCommand> members) {
        scheduler.schedule(notifyGroupAssignToTask.schedulableInstance(
                UUID.randomUUID().toString(), new GroupAssignToTaskCommand(members)));
    }
}
