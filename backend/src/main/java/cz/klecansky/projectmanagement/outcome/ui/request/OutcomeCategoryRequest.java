package cz.klecansky.projectmanagement.outcome.ui.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OutcomeCategoryRequest {

    private String name;

    private String description;

    private UUID project;
}
