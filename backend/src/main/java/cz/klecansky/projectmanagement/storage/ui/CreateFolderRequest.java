package cz.klecansky.projectmanagement.storage.ui;

import lombok.Data;

@Data
public class CreateFolderRequest {
    String path;
    String name;
}
