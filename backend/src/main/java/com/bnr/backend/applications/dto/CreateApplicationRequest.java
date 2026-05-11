package com.bnr.backend.applications.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationRequest {

    @NotBlank
    private String institutionName;

    @NotBlank
    private String licenseType;
}