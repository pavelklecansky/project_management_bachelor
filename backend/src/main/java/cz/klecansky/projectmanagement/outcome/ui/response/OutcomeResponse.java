package cz.klecansky.projectmanagement.outcome.ui.response;

import cz.klecansky.projectmanagement.phase.ui.response.PhaseResponse;
import cz.klecansky.projectmanagement.result.ui.response.ResultResponse;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class OutcomeResponse {

    private UUID id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String description;

    private PhaseResponse phase;

    private OutcomeCategoryResponse outcomeCategory;

    private List<ResultResponse> results;
}
