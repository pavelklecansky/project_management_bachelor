package cz.klecansky.projectmanagement.storage.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;
import java.time.Instant;

@Data
@AllArgsConstructor
public class FileCommand {
    String name;
    Path path;
    Instant modified;
    boolean folder;
}
