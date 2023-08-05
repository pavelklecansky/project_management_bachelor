package cz.klecansky.projectmanagement.storage.ui;

import java.nio.file.Path;
import java.time.Instant;
import lombok.Data;

@Data
public class FileResponse {
    String name;
    Path path;
    Instant modified;
    boolean folder;
}
