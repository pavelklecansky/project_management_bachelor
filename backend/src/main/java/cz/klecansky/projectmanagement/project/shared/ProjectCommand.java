package cz.klecansky.projectmanagement.project.shared;

import cz.klecansky.projectmanagement.budget.shared.BudgetCommand;
import cz.klecansky.projectmanagement.comment.shared.CommentCommand;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import cz.klecansky.projectmanagement.schedule.shared.ScheduleCommand;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ProjectCommand {

    private UUID id;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private List<PhaseCommand> phases;

    private List<TaskCommand> tasks;

    private List<CommentCommand> comments;

    private List<UserCommand> members;

    private List<GroupCommand> memberGroups;

    private List<OutcomeCategoryCommand> outcomeCategories;

    private List<ScheduleCommand> scheduleEntity;

    private List<BudgetCommand> budget;
}
