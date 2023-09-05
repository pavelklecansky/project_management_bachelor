package cz.klecansky.projectmanagement.outcome.ui.request;

import java.util.UUID;

public record OutcomeCategoryUpsertRequest(UUID id, String name, String description, UUID projectId) {}
