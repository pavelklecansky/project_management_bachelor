package cz.klecansky.projectmanagement.project.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.project.service.ProjectService;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class UUIDToProjectCommandConverter implements Converter<UUID, ProjectCommand> {

    @NonNull ProjectService projectService;

    @Override
    public ProjectCommand convert(UUID source) {
        return projectService.get(source).orElseThrow(() -> new NoSuchElementFoundException("Project was not found."));
    }
}
