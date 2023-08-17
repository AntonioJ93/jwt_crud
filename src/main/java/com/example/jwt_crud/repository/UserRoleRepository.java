package com.example.jwt_crud.repository;

import com.example.jwt_crud.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
