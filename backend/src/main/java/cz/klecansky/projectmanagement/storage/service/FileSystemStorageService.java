package cz.klecansky.projectmanagement.storage.service;

import cz.klecansky.projectmanagement.storage.shared.FileCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path root;

    public FileSystemStorageService(@Value("${appstorage.path}") String appStoragePath) throws IOException {
        root = Paths.get(appStoragePath);
        if (Files.notExists(root)) {
            Files.createDirectories(root);
        }
    }

    @Override
    public List<FileCommand> list(Path path) throws IOException {
        return Files.list(root.resolve(path)).map(Path::normalize).map(this::pathToFileCommand).toList();
    }

    @Override
    public void createDictionaryInRoot(UUID id) {
        try {
            Files.createDirectories(root.resolve(id.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource loadFileAsResource(Path path, String fileName) throws IOException {
        try {
            Path filePath = root.resolve(Paths.get(path.toString(), fileName)).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new IOException("File not found " + fileName, ex);
        }
    }

    @Override
    public boolean deleteFile(Path path, String name) throws IOException {
        Path filePath = root.resolve(Paths.get(path.toString(), name)).normalize();
        return Files.deleteIfExists(filePath);
    }

    @Override
    public String storeFile(Path path, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IOException("Filename contains invalid path sequence " + fileName);
        }
        Path targetLocation = root.resolve(Paths.get(path.toString(), fileName));
        if (Files.exists(targetLocation)) {
            throw new IOException("Filename is already used " + fileName);
        }
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName, ex);
        }
    }

    @Override
    public void editFileName(Path path, String oldName, String newName) throws IOException {
        Path filePath = root.resolve(Paths.get(path.toString(), oldName));
        try {
            Files.move(filePath, filePath.resolveSibling(newName));
        } catch (IOException ex) {
            throw new IOException("Could not be rename", ex);
        }

    }

    @Override
    public void createFolder(Path path, String name) {
        Path folderPath = root.resolve(Paths.get(path.toString(), name));
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteFolder(Path path, String name) throws IOException {
        Path folderPath = root.resolve(Paths.get(path.toString(), name)).normalize();
        return FileSystemUtils.deleteRecursively(folderPath);
    }

    private FileCommand pathToFileCommand(Path path) {
        BasicFileAttributes attr;
        try {
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new FileCommand(
                path.getFileName().toString(),
                path.toAbsolutePath(),
                attr.lastModifiedTime().toInstant(),
                attr.isDirectory()
        );
    }
}
