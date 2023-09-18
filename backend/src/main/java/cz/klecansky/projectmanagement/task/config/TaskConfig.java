package cz.klecansky.projectmanagement.task.config;

import com.github.kagkarlsson.scheduler.task.Task;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import cz.klecansky.projectmanagement.task.shared.GroupAssignToTaskCommand;
import cz.klecansky.projectmanagement.task.shared.UserAssignToTaskCommand;
import cz.klecansky.projectmanagement.user.service.EmailService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TaskConfig {

    @NonNull
    EmailService emailService;

    @Bean
    public Task<GroupAssignToTaskCommand> notifyGroupAssignToTask() {
        return Tasks.oneTime("notify-group-assign-to-task", GroupAssignToTaskCommand.class)
                .execute((taskInstance, executionContext) ->
                        emailService.sendAssignToGroupTaskEmail(taskInstance.getData()));
    }

    @Bean
    public Task<UserAssignToTaskCommand> notifyUserAssignToTask() {
        return Tasks.oneTime("notify-user-assign-to-task", UserAssignToTaskCommand.class)
                .execute(
                        (taskInstance, executionContext) -> emailService.sendAssignToTaskEmail(taskInstance.getData()));
    }
}
