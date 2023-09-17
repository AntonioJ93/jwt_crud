package com.example.jwt_crud.config;

import com.example.jwt_crud.security.jwt.JwtEntryPoint;
import com.example.jwt_crud.security.jwt.JwtFilter;
import com.example.jwt_crud.security.service.UserProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtEntryPoint jwtEntryPoint;
    private final JwtFilter jwtFilter;
    private final UserProviderService userProviderService;
    private final PasswordEncoder passwordEncoder;
    private  AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder=http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(userProviderService).passwordEncoder(passwordEncoder);
        authenticationManager=managerBuilder.build();

        http.authenticationManager(authenticationManager);
        http.csrf(c->c.disable());
        http.cors(c->c.disable())
            .authorizeHttpRequests(
                    r->r.requestMatchers("/auth/**").permitAll().anyRequest().authenticated()
            );
        http.exceptionHandling(e->e.authenticationEntryPoint(jwtEntryPoint));
        http.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
