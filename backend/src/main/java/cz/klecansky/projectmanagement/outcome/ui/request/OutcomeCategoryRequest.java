package cz.klecansky.projectmanagement.outcome.ui.request;

import java.util.UUID;
import lombok.Data;

@Data
public class OutcomeCategoryRequest {

    private String name;

    private String description;

    private UUID project;
}
