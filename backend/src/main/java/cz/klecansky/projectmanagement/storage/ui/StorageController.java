package cz.klecansky.projectmanagement.storage.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.FILES_API;

import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.storage.service.StorageService;
import cz.klecansky.projectmanagement.storage.shared.StorageMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(FILES_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StorageController {

    private StorageService storageService;
    private StorageMapper storageMapper;

    @PostMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public List<FileResponse> listFiles(@PathVariable UUID id, @RequestBody PathRequest path) throws IOException {
        String pathPath = Objects.isNull(path.getPath()) ? "" : path.getPath();
        return storageService.list(Path.of(String.valueOf(id), pathPath)).stream()
                .map(storageMapper::fileCommandToFileResponse)
                .toList();
    }

    @PostMapping(path = "{id}/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable UUID id, @PathVariable String name, @RequestBody PathRequest path, HttpServletRequest request)
            throws IOException {
        String pathPath = Objects.isNull(path.getPath()) ? "" : path.getPath();
        Resource resource = storageService.loadFileAsResource(Path.of(String.valueOf(id), pathPath), name);
        String contentType = null;
        try {
            contentType =
                    request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ignored) {
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping(path = "{id}/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteFile(@PathVariable UUID id, @RequestBody PathRequest path, @PathVariable String name)
            throws IOException {
        String pathPath = Objects.isNull(path.getPath()) ? "" : path.getPath();
        if (storageService.deleteFile(Path.of(String.valueOf(id), pathPath), name)) {
            return ResponseEntity.ok()
                    .body(SuccessResponse.builder()
                            .message("File was successfully deleted.")
                            .build());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "folder/{id}/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteFile(@PathVariable UUID id, @PathVariable String name, @RequestBody PathRequest path)
            throws IOException {
        String pathPath = Objects.isNull(path.getPath()) ? "" : path.getPath();
        if (storageService.deleteFolder(Path.of(String.valueOf(id), pathPath), name)) {
            return ResponseEntity.ok()
                    .body(SuccessResponse.builder()
                            .message("Folder was successfully deleted.")
                            .build());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "folder/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createFolder(@PathVariable UUID id, @RequestBody CreateFolderRequest path) {
        String pathPath = Objects.isNull(path.getPath()) ? "" : path.getPath();
        storageService.createFolder(Path.of(String.valueOf(id), pathPath), path.getName());
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Folder was successfully created.")
                .build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editFileName(@PathVariable UUID id, @RequestBody @Valid EditFileNameRequest request)
            throws IOException {
        String pathPath = Objects.isNull(request.getPath()) ? "" : request.getPath();
        storageService.editFileName(Path.of(String.valueOf(id), pathPath), request.oldName, request.newName);
        return ResponseEntity.ok()
                .body(SuccessResponse.builder()
                        .message("File/Folder was successfully renamed.")
                        .build());
    }

    @PostMapping("uploadFile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> uploadFile(
            @PathVariable UUID id, @RequestParam("path") String path, @RequestParam("file") MultipartFile file)
            throws IOException {
        String pathPath = Objects.isNull(path) ? "" : path;
        String fileName = storageService.storeFile(Path.of(String.valueOf(id), pathPath), file);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("File " + fileName + " was successfully uploaded.")
                .build());
    }
}
