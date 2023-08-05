package cz.klecansky.projectmanagement.result.ui.request;

import java.util.UUID;
import lombok.Data;

@Data
public class ResultRequest {

    private String name;

    private String description;

    private UUID outcome;
}
