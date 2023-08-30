package cz.klecansky.projectmanagement.security.jwt;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @NonNull
    TokenProvider tokenProvider;

    @NonNull
    AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(HttpSecurity builder) {
        JWTFilter customFilter = new JWTFilter(tokenProvider, authenticationEntryPoint);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
