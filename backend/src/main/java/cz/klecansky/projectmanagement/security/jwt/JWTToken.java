package cz.klecansky.projectmanagement.security.jwt;

import lombok.Value;

@Value
public class JWTToken {
    String accessToken;
    String tokenType;
    long expirationTime;
}
