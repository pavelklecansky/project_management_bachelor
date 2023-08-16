package cz.klecansky.projectmanagement.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "Authorization";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String[] PUBLIC_URLS = {
        "/**",
        "/api/users/login",
        "/api/users/register",
        "/api/users/passwordReset",
        "/api/users/register/token/**",
        "/api/users/passwordReset/check",
        "/api/users/newPassword"
    };
}
