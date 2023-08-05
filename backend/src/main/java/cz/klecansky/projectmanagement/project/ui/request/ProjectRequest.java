package cz.klecansky.projectmanagement.project.ui.request;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;
}
