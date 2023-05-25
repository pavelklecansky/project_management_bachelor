package cz.klecansky.projectmanagement.comment.shared;

import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.comment.ui.request.CommentRequest;
import cz.klecansky.projectmanagement.comment.ui.response.CommentResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentMapper {

    @NonNull ModelMapper modelMapper;

    public CommentEntity commentCommandToCommentEntity(CommentCommand commentCommand) {
        return modelMapper.map(commentCommand, CommentEntity.class);
    }

    public CommentCommand commentEntityToCommentCommand(CommentEntity commentEntity) {
        return modelMapper.map(commentEntity, CommentCommand.class);
    }

    public CommentCommand commentRequestToCommentCommand(CommentRequest commentRequest) {
        return modelMapper.map(commentRequest, CommentCommand.class);
    }

    public CommentResponse commentCommandToCommentResponse(CommentCommand commentCommand) {
        return modelMapper.map(commentCommand, CommentResponse.class);
    }
}
