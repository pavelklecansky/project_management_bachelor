package cz.klecansky.projectmanagement.schedule.shared;

import cz.klecansky.projectmanagement.schedule.io.RowEntity;
import cz.klecansky.projectmanagement.schedule.ui.request.RowRequest;
import cz.klecansky.projectmanagement.schedule.ui.response.RowResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RowMapper {

    @NonNull
    ModelMapper modelMapper;

    public RowEntity rowCommandToRowEntity(RowCommand rowCommand) {
        return modelMapper.map(rowCommand, RowEntity.class);
    }

    public RowCommand rowEntityToRowCommand(RowEntity rowEntity) {
        return modelMapper.map(rowEntity, RowCommand.class);
    }

    public RowCommand rowRequestToRowCommand(RowRequest rowRequest) {
        return modelMapper.map(rowRequest, RowCommand.class);
    }

    public RowResponse rowCommandToRowResponse(RowCommand rowCommand) {
        return modelMapper.map(rowCommand, RowResponse.class);
    }
}
