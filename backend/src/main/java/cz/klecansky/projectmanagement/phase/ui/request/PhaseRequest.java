package cz.klecansky.projectmanagement.phase.ui.request;

import lombok.Data;

import java.time.Instant;

@Data
public class PhaseRequest {

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String note;
}
