package cz.klecansky.projectmanagement.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

    TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);
        String requestURI = request.getRequestURI();

        try {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                LOG.debug("Authentication to security context is set for '{}', uri: {}", auth.getName(), requestURI);
            } else {
                LOG.debug("no valid JWT token found, uri: {}", requestURI);
            }
        } catch (Exception e) {
            handleInvalidCorrelationId(response, e);
            return; // make sure you have this set!
        }

        filterChain.doFilter(request, response);
    }

    private void handleInvalidCorrelationId(HttpServletResponse response, Exception exception) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
    }
}
