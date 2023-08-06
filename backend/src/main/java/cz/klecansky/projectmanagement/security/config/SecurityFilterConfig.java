package cz.klecansky.projectmanagement.security.config;

import cz.klecansky.projectmanagement.security.jwt.JWTAccessDeniedHandler;
import cz.klecansky.projectmanagement.security.jwt.JWTAuthenticationEntryPoint;
import cz.klecansky.projectmanagement.security.jwt.JWTConfigurer;
import cz.klecansky.projectmanagement.security.jwt.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static cz.klecansky.projectmanagement.security.SecurityConstants.PUBLIC_URLS;

@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Configuration
public class SecurityFilterConfig {
    TokenProvider tokenProvider;
    JWTAccessDeniedHandler jwtAccessDeniedHandler;
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler);
                    exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                })
//
//
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authorize) -> authorize.requestMatchers(PUBLIC_URLS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .apply(new JWTConfigurer(tokenProvider));
        return http.build();
    }



}
