package cz.klecansky.projectmanagement.project.shared;

import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.project.ui.request.ProjectRequest;
import cz.klecansky.projectmanagement.project.ui.response.ProjectResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectMapper {

    @NonNull
    ModelMapper modelMapper;

    public ProjectEntity projectCommandToProjectEntity(ProjectCommand projectCommand) {
        return modelMapper.map(projectCommand, ProjectEntity.class);
    }

    public ProjectCommand projectEntityToProjectCommand(ProjectEntity projectEntity) {
        return modelMapper.map(projectEntity, ProjectCommand.class);
    }

    public ProjectCommand projectRequestToProjectCommand(ProjectRequest projectRequest) {
        return modelMapper.map(projectRequest, ProjectCommand.class);
    }

    public ProjectResponse projectCommandToProjectResponse(ProjectCommand projectCommand) {
        return modelMapper.map(projectCommand, ProjectResponse.class);
    }
}
