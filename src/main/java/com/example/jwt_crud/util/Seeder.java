package com.example.jwt_crud.util;

import com.example.jwt_crud.model.entity.User;
import com.example.jwt_crud.model.entity.UserRole;
import com.example.jwt_crud.repository.UserRepository;
import com.example.jwt_crud.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class Seeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    @Override
    @Transactional
    public void run(String... args) throws Exception {


        if(roleRepository.count()==0){
            log.info("ejecutando seeder");
            // UserRole
            UserRole adminRole= UserRole.builder()
                    .name("ROLE_ADMIN")
                    .description("Administrador")
                    .build();
            UserRole userRole=UserRole.builder()
                    .name("ROLE_USER")
                    .description("Usuario")
                    .build();
            // User
            User adminUser = User.builder()
                    .username("admin")
                    .password(encoder.encode("Test123"))
                    .name("admin")
                    .surname("user")
                    .email("admin@admin.com")
                    .phone("666555444")
                    .address("Calle 1 Nº22")
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .enable(true)
                    .userRoles(Set.of(adminRole,userRole))
                    .build();

            User basicUser = User.builder()
                    .username("basic")
                    .password(encoder.encode("Test123"))
                    .name("basic")
                    .surname("user")
                    .email("user@user.com")
                    .phone("654321000")
                    .address("Calle 2 Nº23")
                    .birthDate(LocalDate.of(1993, 1, 19))
                    .userRoles(Set.of(userRole))
                    .enable(true)
                    .build();

            adminRole.addUser(adminUser);
            userRole.addUser(adminUser);
            userRole.addUser(basicUser);
            log.info("Poblando db");
            User guardado1=userRepository.save(adminUser);
            log.info("usuario admin guardado {}",guardado1);
            User guardado2=userRepository.save(basicUser);
            log.info("usuario basico guardado {}",guardado2);
            log.info("Db poblada");
        }
    }
}
