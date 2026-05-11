package com.bnr.backend.users.service;

import com.bnr.backend.common.enums.RoleType;
import com.bnr.backend.roles.entity.Role;
import com.bnr.backend.roles.repository.RoleRepository;
import com.bnr.backend.users.dto.CreateUserRequest;
import com.bnr.backend.users.dto.UserResponse;
import com.bnr.backend.users.entity.User;
import com.bnr.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(role))
                .build();

        User saved = userRepository.save(user);

        return UserResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .fullName(saved.getFullName())
                .role(request.getRole())
                .build();
    }

    public boolean adminExists() {
        return userRepository.existsByRoles_Name(RoleType.ADMIN);
    }
}