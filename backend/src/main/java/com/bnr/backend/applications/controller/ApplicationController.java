package com.bnr.backend.applications.controller;

import com.bnr.backend.applications.dto.ApplicationResponse;
import com.bnr.backend.applications.dto.CreateApplicationRequest;
import com.bnr.backend.applications.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('CREATE_APPLICATION')")
    public ApplicationResponse create(
            @RequestParam("institutionName") String institutionName,
            @RequestParam("licenseType") String licenseType,
            @RequestParam("businessAddress") String businessAddress,
            @RequestParam("MARKET_RISK_MANAGEMENT_POLICY") MultipartFile marketRisk,
            @RequestParam("CLIENT_ASSET_PROTECTION_POLICY") MultipartFile clientAsset,
            @RequestParam("TRADING_POLICY") MultipartFile tradingPolicy
    ) {
        return applicationService.createWithDocuments(
                institutionName,
                licenseType,
                businessAddress,
                marketRisk,
                clientAsset,
                tradingPolicy
        );
    }
    @GetMapping
    public List<ApplicationResponse> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ApplicationResponse findById(@PathVariable UUID id) {
        return applicationService.findById(id);
    }
}