package cz.klecansky.projectmanagement.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

    @NonNull
    TokenProvider tokenProvider;

    @NonNull
    AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);
        String requestURI = request.getRequestURI();

        try {
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                LOG.debug("Authentication to security context is set for '{}', uri: {}", auth.getName(), requestURI);
            } else {
                LOG.debug("no valid JWT token found, uri: {}", requestURI);
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
