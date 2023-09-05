package cz.klecansky.projectmanagement.outcome.shared;

import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryEntity;
import cz.klecansky.projectmanagement.outcome.ui.request.OutcomeCategoryUpsertRequest;
import cz.klecansky.projectmanagement.outcome.ui.response.OutcomeCategoryResponse;
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
public class OutcomeCategoryOldMapper {

    @NonNull
    ModelMapper modelMapper;

    public OutcomeCategoryCommand outcomeCategoryEntityToOutcomeCategoryCommand(
            OutcomeCategoryEntity outcomeCategoryEntity) {
        return modelMapper.map(outcomeCategoryEntity, OutcomeCategoryCommand.class);
    }

    public OutcomeCategoryCommand outcomeCategoryRequestToOutcomeCategoryCommand(
            OutcomeCategoryUpsertRequest outcomeCategoryUpsertRequest) {
        return modelMapper.map(outcomeCategoryUpsertRequest, OutcomeCategoryCommand.class);
    }

    public OutcomeCategoryResponse outcomeCategoryCommandToOutcomeCategoryResponse(
            OutcomeCategoryCommand outcomeCategoryCommand) {
        return modelMapper.map(outcomeCategoryCommand, OutcomeCategoryResponse.class);
    }
}
