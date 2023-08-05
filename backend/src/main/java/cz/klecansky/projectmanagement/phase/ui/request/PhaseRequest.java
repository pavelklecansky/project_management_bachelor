package cz.klecansky.projectmanagement.phase.ui.request;

import java.time.Instant;
import lombok.Data;

@Data
public class PhaseRequest {

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String note;
}
