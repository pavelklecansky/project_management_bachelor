package cz.klecansky.projectmanagement.user.ui.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import cz.klecansky.projectmanagement.security.jwt.JWTToken;
import lombok.Value;

@Value
public class SignInResponse {
    @JsonUnwrapped
    JWTToken jwtToken;
    UserResponse user;
}
