package com.bnr.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private String role;
    private String names;
    private List<String> permission;
}