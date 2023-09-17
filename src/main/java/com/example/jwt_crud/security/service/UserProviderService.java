package com.example.jwt_crud.security.service;

import com.example.jwt_crud.repository.UserRepository;
import com.example.jwt_crud.security.model.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProviderService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional //evitamos el lazy inicialization de los roles
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      log.info("loadUserByUsername==> username== "+username);
        com.example.jwt_crud.model.entity.User usuario=userRepository.findByUsername(username)
                .orElseThrow();
        return userEntityToUserDetails(usuario);
    }

    public UserDetails userEntityToUserDetails(com.example.jwt_crud.model.entity.User user){
        return UserProvider.builder()
                .userRoles(user.getUserRoles().stream()
                        .map(r->new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet())
                )
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .enable(user.getEnable())
                .build();
    }
}
