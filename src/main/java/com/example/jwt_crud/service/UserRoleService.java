package com.example.jwt_crud.service;

import com.example.jwt_crud.model.entity.UserRole;
import com.example.jwt_crud.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }
}
