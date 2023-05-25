package cz.klecansky.projectmanagement.phase.ui.response;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class PhaseResponse {

    private UUID id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String note;
}
