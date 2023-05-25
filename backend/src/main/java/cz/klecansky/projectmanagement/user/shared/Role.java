package cz.klecansky.projectmanagement.user.shared;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_SUPER_ADMIN;

    public String getAuthority() {
        return name();
    }
}
