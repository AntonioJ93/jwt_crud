package com.example.jwt_crud.security.jwt;

import com.example.jwt_crud.security.service.UserProviderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserProviderService userProviderService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String token= getToken(request);
            if (!token.isEmpty() && jwtProvider.validarToken(token)){
                UserDetails userDetails= userProviderService.loadUserByUsername(jwtProvider.getUserNameFromToken(token));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); //autenticamos al usuario
            }
        } catch (UsernameNotFoundException e) {
            log.error("Error JwtFilter "+request.toString());
        }
        chain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        String authorization=request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            log.info("token== "+authorization);
            return authorization.replace("Bearer ","");
        }
        log.error("token null");
        return "";
    }
}
