package com.bnr.backend.users.controller;

import com.bnr.backend.common.enums.RoleType;
import com.bnr.backend.users.dto.CreateUserRequest;
import com.bnr.backend.users.dto.UserResponse;
import com.bnr.backend.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    private final UserService userService;

    @PostMapping("/admin")
    public UserResponse createFirstAdmin(
            @Valid @RequestBody CreateUserRequest request
    ) {
        if (userService.adminExists()) {
            throw new IllegalStateException("Admin already exists");
        }

        request.setRole(RoleType.ADMIN);
        return userService.createUser(request);
    }
}