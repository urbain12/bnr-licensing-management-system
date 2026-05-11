package com.bnr.backend.users.dto;

import com.bnr.backend.common.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponse {

    private UUID id;
    private String email;
    private String fullName;
    private RoleType role;
}