package com.example.jwt_crud.security.jwt;

import com.example.jwt_crud.security.model.UserProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expirationTime;

    public String generarToken(Authentication authentication){
        log.info("generarToken == "+authentication.getPrincipal());
        UserProvider user= (UserProvider) authentication.getPrincipal();
        return Jwts.builder()
                .signWith(getKey())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                new Date().getTime()+expirationTime* 1000L
                        )
                )
                .claim("roles",user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
                )
                .compact();

    }
    public String getUserNameFromToken(String token){
        log.info("getUserNameFromToken == "+Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject());
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validarToken(String token){
        try {
            log.info("validarToken == "+Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody());
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Error ExpiredJwtException "+e.getCause());
        } catch (UnsupportedJwtException e) {
            log.error("Error UnsupportedJwtException "+e.getCause());
        } catch (MalformedJwtException e) {
            log.error("Error MalformedJwtException "+e.getCause());
        } catch (SignatureException e) {
            log.error("Error SignatureException "+e.getCause());
        } catch (IllegalArgumentException e) {
            log.error("Error IllegalArgumentException "+e.getCause());
        }catch (Exception e){
            log.error("Error al validar el token "+e.getCause());
        }
        return false;
    }

    private Key getKey(){
        byte[] secretBytes= Decoders.BASE64.decode(secret);
        log.error("getKey "+Keys.hmacShaKeyFor(secretBytes));
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
