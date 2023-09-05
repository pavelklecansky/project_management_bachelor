package cz.klecansky.projectmanagement.outcome.shared;

import java.util.UUID;

public record OutcomeCategoryUpsertCommand(UUID id, String name, String description, UUID projectId) {}
