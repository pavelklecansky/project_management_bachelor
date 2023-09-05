package cz.klecansky.projectmanagement.comment.shared;

import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.comment.ui.request.CommentCreationRequest;
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
public class CommentMapper {

    @NonNull
    ModelMapper modelMapper;

    public CommentEntity commentCommandToCommentEntity(CommentCommand commentCommand) {
        return modelMapper.map(commentCommand, CommentEntity.class);
    }

    public CommentCommand commentRequestToCommentCommand(CommentCreationRequest commentCreationRequest) {
        return modelMapper.map(commentCreationRequest, CommentCommand.class);
    }
}
