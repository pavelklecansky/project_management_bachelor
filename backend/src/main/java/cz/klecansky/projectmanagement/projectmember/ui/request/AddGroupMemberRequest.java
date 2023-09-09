package cz.klecansky.projectmanagement.projectmember.ui.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddGroupMemberRequest(@NotNull UUID group) {}
