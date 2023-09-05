package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeCategoryUpsertRequest;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeUpsertRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OutcomeMapper {

    OutcomeUpsertCommand outcomeUpsertRequestToOutcomeUpsertCommand(OutcomeUpsertRequest outcomeUpsertRequest);

    OutcomeCategoryUpsertCommand outcomeCategoryUpsertRequestToOutcomeCategoryUpsertCommand(
            OutcomeCategoryUpsertRequest outcomeUpsertRequest);
}
