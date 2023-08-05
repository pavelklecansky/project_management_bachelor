package cz.klecansky.projectmanagement.outcome.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class OutcomeCategoryResponse {

    private UUID id;

    private String name;

    private String description;
}
