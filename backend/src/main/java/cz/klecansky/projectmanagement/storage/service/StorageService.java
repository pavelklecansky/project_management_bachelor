package cz.klecansky.projectmanagement.storage.service;

import cz.klecansky.projectmanagement.storage.shared.FileCommand;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface StorageService {
    List<FileCommand> list(Path path) throws IOException;
    void createDictionaryInRoot(UUID id);
    Resource loadFileAsResource(Path path, String fileName) throws IOException;
    boolean deleteFile(Path path, String name) throws IOException;
    String storeFile(Path path, MultipartFile file) throws IOException;
    void editFileName(Path path, String oldName, String newName) throws IOException;
    void createFolder(Path of, String name);
    boolean deleteFolder(Path path, String name) throws IOException;
}
