package com.bnr.backend.audit.controller;

import com.bnr.backend.audit.dto.AuditResponse;
import com.bnr.backend.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications/{id}/audit-trail")
@RequiredArgsConstructor
public class AuditController {

    private final AuditRepository auditRepository;

    @GetMapping
    public List<AuditResponse> getAudit(@PathVariable UUID id) {
        return auditRepository.findByApplication_IdOrderByTimestampDesc(id)
                .stream()
                .map(a -> AuditResponse.builder()
                        .actor(a.getActor().getFullName())
                        .action(a.getAction())
                        .previousState(a.getPreviousState())
                        .newState(a.getNewState())
                        .timestamp(a.getTimestamp())
                        .comment(a.getComment())
                        .build())
                .toList();
    }
}