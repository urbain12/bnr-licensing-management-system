package com.bnr.backend.workflow.controller;

import com.bnr.backend.applications.dto.ApplicationResponse;
import com.bnr.backend.common.dto.CommentRequest;
import com.bnr.backend.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final WorkflowService workflowService;

    @PostMapping("/{id}/start")
    @PreAuthorize("hasAuthority('REVIEW_APPLICATION')")
    public ApplicationResponse start(@PathVariable UUID id) {
        return workflowService.startReview(id);
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('REVIEW_APPLICATION')")
    public ApplicationResponse complete(
            @PathVariable UUID id,
            @RequestBody(required = false) CommentRequest request
    ) {
        return workflowService.completeReview(
                id,
                request == null ? null : request.getComment()
        );
    }

    @PostMapping("/{id}/request-info")
    @PreAuthorize("hasAuthority('REQUEST_INFORMATION')")
    public ApplicationResponse requestInfo(
            @PathVariable UUID id,
            @RequestBody(required = false) CommentRequest request
    ) {
        return workflowService.requestInfo(
                id,
                request == null ? null : request.getComment()
        );
    }
}