package cz.klecansky.projectmanagement.storage.ui;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditFileNameRequest {
    @NotNull
    String oldName;

    @NotNull
    String newName;

    String path;
}
