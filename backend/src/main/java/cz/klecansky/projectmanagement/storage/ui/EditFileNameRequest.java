package cz.klecansky.projectmanagement.storage.ui;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditFileNameRequest {
    @NotNull
    String oldName;

    @NotNull
    String newName;

    String path;
}
