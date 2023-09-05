package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.outcome.io.OutcomeEntity;
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
@Deprecated
public class OutcomeMapperOld {

    @NonNull
    ModelMapper modelMapper;

    public OutcomeCommand outcomeEntityToOutcomeCommand(OutcomeEntity outcomeEntity) {
        return modelMapper.map(outcomeEntity, OutcomeCommand.class);
    }

    public OutcomeResponse outcomeCommandToOutcomeResponse(OutcomeCommand outcomeCommand) {
        return modelMapper.map(outcomeCommand, OutcomeResponse.class);
    }
}
