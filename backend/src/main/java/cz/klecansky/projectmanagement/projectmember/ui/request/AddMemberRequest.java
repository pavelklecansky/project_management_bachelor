package cz.klecansky.projectmanagement.projectmember.ui.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddMemberRequest(@NotNull UUID user) {}
