package com.bnr.backend.audit.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AuditResponse {

    private String actor;
    private String action;
    private String previousState;
    private String newState;
    private LocalDateTime timestamp;
    private String comment;
}