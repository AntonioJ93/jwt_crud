package com.example.jwt_crud.controller;

import com.example.jwt_crud.controller.route.UserRoutes;
import com.example.jwt_crud.model.entity.User;
import com.example.jwt_crud.model.entity.UserRole;
import com.example.jwt_crud.service.UserRoleService;
import com.example.jwt_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(UserRoutes.USER)
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    @GetMapping
    public List<User> test() {
        return userService.findAll();
    }

    @GetMapping("/rol")
    public List<UserRole> test2() {
        return userRoleService.findAll();

    }
}
