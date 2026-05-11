package com.bnr.backend.workflow.controller;

import com.bnr.backend.applications.dto.ApplicationResponse;
import com.bnr.backend.common.dto.CommentRequest;
import com.bnr.backend.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final WorkflowService workflowService;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_APPLICATION')")
    public ApplicationResponse approve(
            @PathVariable UUID id,
            @RequestBody(required = false) CommentRequest request
    ) {
        return workflowService.approve(
                id,
                request == null ? null : request.getComment()
        );
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('REJECT_APPLICATION')")
    public ApplicationResponse reject(
            @PathVariable UUID id,
            @RequestBody(required = false) CommentRequest request
    ) {
        return workflowService.reject(
                id,
                request == null ? null : request.getComment()
        );
    }
}