package com.example.jwt_crud.controller;

import com.example.jwt_crud.controller.route.AuthRoutes;
import com.example.jwt_crud.model.resquestDto.LoginDto;
import com.example.jwt_crud.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthRoutes.AUTH)
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    @PostMapping(AuthRoutes.LOGIN)
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(),loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(jwtProvider.generarToken(authentication));
    }
}
