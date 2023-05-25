package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.outcome.io.OutcomeEntity;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeRequest;
import cz.klecansky.projectmanagement.outcome.ui.response.OutcomeResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OutcomeMapper {

    @NonNull ModelMapper modelMapper;

    public OutcomeEntity outcomeCommandToOutcomeEntity(OutcomeCommand outcomeCommand) {
        return modelMapper.map(outcomeCommand, OutcomeEntity.class);
    }

    public OutcomeCommand outcomeEntityToOutcomeCommand(OutcomeEntity outcomeEntity) {
        return modelMapper.map(outcomeEntity, OutcomeCommand.class);
    }

    public OutcomeCommand outcomeRequestToOutcomeCommand(OutcomeRequest outcomeRequest) {
        return modelMapper.map(outcomeRequest, OutcomeCommand.class);
    }

    public OutcomeResponse outcomeCommandToOutcomeResponse(OutcomeCommand outcomeCommand) {
        return modelMapper.map(outcomeCommand, OutcomeResponse.class);
    }
}
