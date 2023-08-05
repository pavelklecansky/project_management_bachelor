package cz.klecansky.projectmanagement.storage.shared;

import java.nio.file.Path;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileCommand {
    String name;
    Path path;
    Instant modified;
    boolean folder;
}
