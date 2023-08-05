package cz.klecansky.projectmanagement.security;

import cz.klecansky.projectmanagement.user.shared.Role;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class SecurityUtils {
    public static boolean isAdmin(UserDetails userPrincipal) {
        return userPrincipal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")
                        || a.getAuthority().equals("ROLE_SUPER_ADMIN"));
    }

    public static boolean isSuperAdmin(UserDetails userPrincipal) {
        return userPrincipal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPER_ADMIN"));
    }

    public static boolean loginUserIsSuperAdmin() {
        UserDetails loginUser = (UserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return isSuperAdmin(loginUser);
    }

    public static boolean isAdmin(List<Role> roles) {
        return roles.stream().anyMatch(role -> role.equals(Role.ROLE_ADMIN) || role.equals(Role.ROLE_SUPER_ADMIN));
    }

    public static UserPrincipal getCurrentUser() {
        return (UserPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
