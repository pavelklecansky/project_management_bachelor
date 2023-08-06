package cz.klecansky.projectmanagement.project.ui.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
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
