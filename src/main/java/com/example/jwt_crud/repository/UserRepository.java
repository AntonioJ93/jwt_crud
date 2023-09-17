package com.example.jwt_crud.repository;

import com.example.jwt_crud.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByUsername(String username);
}

