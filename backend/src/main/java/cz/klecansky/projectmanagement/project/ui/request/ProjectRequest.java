package cz.klecansky.projectmanagement.project.ui.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

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
