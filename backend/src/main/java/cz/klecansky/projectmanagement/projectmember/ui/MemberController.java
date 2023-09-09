package cz.klecansky.projectmanagement.projectmember.ui;

import static cz.klecansky.projectmanagement.core.WebConstants.PROJECTS_API;

import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.project.shared.OldProjectMapper;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.projectmember.service.ProjectMemberService;
import cz.klecansky.projectmanagement.projectmember.ui.request.AddGroupMemberRequest;
import cz.klecansky.projectmanagement.projectmember.ui.request.AddMemberRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PROJECTS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MemberController {

    @NonNull
    ProjectMemberService projectMemberService;

    @NonNull
    OldProjectMapper oldProjectMapper;

    @PostMapping(path = "{id}/member")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> addMember(
            @PathVariable UUID id, @Valid @RequestBody AddMemberRequest request) {
        ProjectCommand updatedProject = projectMemberService.addMember(id, request.user());
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Member was successfully added.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = "{id}/member/{idMember}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteMember(@PathVariable UUID id, @PathVariable UUID idMember) {
        ProjectCommand updatedProject = projectMemberService.deleteMember(id, idMember);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Member was successfully deleted.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @PostMapping(path = "{id}/group-member")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> addGroupMember(
            @PathVariable UUID id, @Valid @RequestBody AddGroupMemberRequest request) {
        ProjectCommand updatedProject = projectMemberService.addGroupMember(id, request.group());
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Group member was successfully added.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }

    @DeleteMapping(path = "{id}/group-member/{idMember}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse<?>> deleteGroupMember(@PathVariable UUID id, @PathVariable UUID idMember) {
        ProjectCommand updatedProject = projectMemberService.deleteGroupMember(id, idMember);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Group member was successfully deleted.")
                .data(oldProjectMapper.projectCommandToProjectResponse(updatedProject))
                .build());
    }
}
