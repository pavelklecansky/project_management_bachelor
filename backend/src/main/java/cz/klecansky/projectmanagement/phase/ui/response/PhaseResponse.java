package cz.klecansky.projectmanagement.phase.ui.response;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class PhaseResponse {

    private UUID id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String note;
}
