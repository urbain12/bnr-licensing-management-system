package com.bnr.backend.auth.service;

import com.bnr.backend.auth.dto.LoginRequest;
import com.bnr.backend.auth.dto.LoginResponse;
import com.bnr.backend.auth.jwt.JwtService;
import com.bnr.backend.users.entity.User;
import com.bnr.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        String role = user.getRoles()
                .stream()
                .findFirst()
                .map(r -> r.getName().name())
                .orElse(null);

        List<String> permissions = user.getRoles()
                .stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(p -> p.getName().name())
                .distinct()
                .toList();

        return LoginResponse.builder()
                .token(token)
                .role(role)
                .names(user.getFullName())
                .permission(permissions)
                .build();
    }
}