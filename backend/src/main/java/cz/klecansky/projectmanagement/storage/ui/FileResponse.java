package cz.klecansky.projectmanagement.storage.ui;

import lombok.Data;

import java.nio.file.Path;
import java.time.Instant;

@Data
public class FileResponse {
    String name;
    Path path;
    Instant modified;
    boolean folder;
}
