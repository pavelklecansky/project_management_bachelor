package cz.klecansky.projectmanagement.storage.shared;

import cz.klecansky.projectmanagement.storage.ui.FileResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StorageMapper {
    @NonNull ModelMapper modelMapper;

    public FileResponse fileCommandToFileResponse(FileCommand fileCommand) {
        return modelMapper.map(fileCommand, FileResponse.class);
    }
}
