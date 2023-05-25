package cz.klecansky.projectmanagement.group.ui;


import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.core.response.SuccessResponse;
import cz.klecansky.projectmanagement.group.service.GroupService;
import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.group.shared.GroupMemberCommand;
import cz.klecansky.projectmanagement.group.shared.mappers.GroupMapper;
import cz.klecansky.projectmanagement.group.ui.request.GroupRequest;
import cz.klecansky.projectmanagement.group.ui.response.GroupResponse;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static cz.klecansky.projectmanagement.core.WebConstants.GROUPS_API;


@RestController
@RequestMapping(GROUPS_API)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GroupController {

    @NonNull GroupService groupService;
    @NonNull Converter<UUID, UserCommand> UUIDToUserCommandConverter;
    @NonNull GroupMapper groupMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getGroups() {
        List<GroupResponse> groupResponses = groupService.getGroups().stream().map(groupMapper::groupCommandToGroupResponse).toList();
        return ResponseEntity.ok(groupResponses);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getGroup(@PathVariable UUID id) throws NoSuchElementFoundException {
        return groupService.getGroup(id)
                .map(organizationCommand -> ResponseEntity.ok(groupMapper.groupCommandToGroupResponse(organizationCommand)))
                .orElseThrow(NoSuchElementFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> createGroup(@RequestBody GroupRequest request) {
        GroupCommand groupCommand = groupMapper.groupRequestToGroupCommand(request);
        groupCommand.setMembers(request.getMembers().stream().map(groupMemberCommand -> {
            GroupMemberCommand memberCommand = new GroupMemberCommand();
            memberCommand.setPosition(groupMemberCommand.getPosition());
            memberCommand.setUser(UUIDToUserCommandConverter.convert(groupMemberCommand.getUser()));
            return memberCommand;
        }).toList());
        GroupCommand createdGroup = groupService.createGroup(groupCommand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/groups/{id}")
                .buildAndExpand(createdGroup.getId()).toUri();
        return ResponseEntity.created(location).body(SuccessResponse.builder().message("Group was created successfully.").data(groupMapper.groupCommandToGroupResponse(createdGroup)).build());
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Group was successfully deleted.").build());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse<GroupResponse>> updateGroup(@PathVariable UUID id, @RequestBody GroupRequest request) {
        GroupCommand groupCommand = groupMapper.groupRequestToGroupCommand(request);
        groupCommand.setMembers(request.getMembers().stream().map(groupMemberCommand -> {
            GroupMemberCommand memberCommand = new GroupMemberCommand();
            memberCommand.setId(Objects.isNull(groupMemberCommand.getId()) ? UUID.randomUUID() : groupMemberCommand.getId());
            memberCommand.setPosition(groupMemberCommand.getPosition());
            memberCommand.setUser(UUIDToUserCommandConverter.convert(groupMemberCommand.getUser()));
            return memberCommand;
        }).toList());
        GroupCommand update = groupService.updateGroup(id, groupCommand);
        return ResponseEntity.ok(SuccessResponse.<GroupResponse>builder().message("Group was successfully updated.").data(groupMapper.groupCommandToGroupResponse(update)).build());
    }

}
