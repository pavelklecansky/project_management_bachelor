package cz.klecansky.projectmanagement.project.utils;

import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.project.shared.ProjectCommand;
import cz.klecansky.projectmanagement.security.UserPrincipal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectUtils {

    public static boolean memberOfProject(ProjectCommand projectCommand, UserPrincipal userPrincipal) {
        return isMember(projectCommand, userPrincipal) || isGroupMember(projectCommand, userPrincipal);
    }

    private static boolean isMember(ProjectCommand projectCommand, UserPrincipal userPrincipal) {
        return projectCommand.getMembers().stream().anyMatch(userCommand -> userCommand.getId().equals(userPrincipal.getUser().getId()));
    }

    private static boolean isGroupMember(ProjectCommand projectCommand, UserPrincipal userPrincipal) {
        return projectCommand.getMemberGroups().stream().anyMatch(groupCommand -> isMemberOfGroup(groupCommand, userPrincipal));
    }

    private static boolean isMemberOfGroup(GroupCommand groupCommand, UserPrincipal userPrincipal) {
        return groupCommand.getMembers().stream().anyMatch(memberCommand -> memberCommand.getUser().getId().equals(userPrincipal.getUser().getId()));
    }
}
