package cz.klecansky.projectmanagement.result.shared;

import cz.klecansky.projectmanagement.result.io.ResultEntity;
import cz.klecansky.projectmanagement.result.ui.request.ResultRequest;
import cz.klecansky.projectmanagement.result.ui.response.ResultResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ResultMapper {

    @NonNull
    ModelMapper modelMapper;

    public ResultEntity resultCommandToResultEntity(ResultCommand resultCommand) {
        return modelMapper.map(resultCommand, ResultEntity.class);
    }

    public ResultCommand resultEntityToResultCommand(ResultEntity resultEntity) {
        return modelMapper.map(resultEntity, ResultCommand.class);
    }

    public ResultCommand resultRequestToResultCommand(ResultRequest resultRequest) {
        return modelMapper.map(resultRequest, ResultCommand.class);
    }

    public ResultResponse resultCommandToResultResponse(ResultCommand resultCommand) {
        return modelMapper.map(resultCommand, ResultResponse.class);
    }
}
