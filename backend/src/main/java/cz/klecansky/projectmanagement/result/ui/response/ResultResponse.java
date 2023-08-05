package cz.klecansky.projectmanagement.result.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class ResultResponse {

    private UUID id;

    private String name;

    private String description;
}
