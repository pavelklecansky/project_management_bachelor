package cz.klecansky.projectmanagement.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
