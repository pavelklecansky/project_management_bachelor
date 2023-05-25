package cz.klecansky.projectmanagement.phase.shared;

import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.phase.ui.request.PhaseRequest;
import cz.klecansky.projectmanagement.phase.ui.response.PhaseResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PhaseMapper {

    @NonNull ModelMapper modelMapper;

    public PhaseEntity phaseCommandToPhaseEntity(PhaseCommand phaseCommand) {
        return modelMapper.map(phaseCommand, PhaseEntity.class);
    }

    public PhaseCommand phaseEntityToPhaseCommand(PhaseEntity phaseEntity) {
        return modelMapper.map(phaseEntity, PhaseCommand.class);
    }

    public PhaseCommand phaseRequestToPhaseCommand(PhaseRequest phaseRequest) {
        return modelMapper.map(phaseRequest, PhaseCommand.class);
    }

    public PhaseResponse phaseCommandToPhaseResponse(PhaseCommand phaseCommand) {
        return modelMapper.map(phaseCommand, PhaseResponse.class);
    }
}
