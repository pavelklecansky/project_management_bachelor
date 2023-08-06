package cz.klecansky.projectmanagement.security.jwt;

import cz.klecansky.projectmanagement.security.UserPrincipal;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.klecansky.projectmanagement.security.SecurityConstants.*;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expire-length:3600000}")
    private long validityInMilliseconds;

    private Key key;

    @NonNull
    private final UserRepository userRepository;

    public TokenProvider(@NonNull @Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public JWTToken createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date validity = new Date(System.currentTimeMillis() + validityInMilliseconds);

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new JWTToken(token, TOKEN_TYPE, validityInMilliseconds);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(claims.getSubject());
        if (userEntity.isPresent()) {
            UserPrincipal userPrincipal = new UserPrincipal(userEntity.get());
            return new UsernamePasswordAuthenticationToken(userPrincipal, token, userPrincipal.getAuthorities());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORITIES);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
