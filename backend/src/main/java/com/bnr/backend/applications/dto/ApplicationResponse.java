package com.bnr.backend.applications.dto;

import com.bnr.backend.applications.entity.ApplicationState;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ApplicationResponse {

    private UUID id;
    private String name;
    private String referenceNumber;
    private String licenseType;
    private ApplicationState status;
}