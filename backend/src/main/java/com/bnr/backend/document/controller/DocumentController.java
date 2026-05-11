package com.bnr.backend.document.controller;

import com.bnr.backend.document.dto.DocumentResponse;
import com.bnr.backend.document.repository.ApplicationDocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications/{id}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final ApplicationDocumentVersionRepository versionRepository;

    @GetMapping
    public List<DocumentResponse> getDocuments(@PathVariable UUID id) {
        return versionRepository.findByDocument_Application_IdOrderByUploadedAtDesc(id)
                .stream()
                .map(v -> DocumentResponse.builder()
                        .documentType(v.getDocument().getDocumentType().name())
                        .versionNumber(v.getVersionNumber())
                        .filename(v.getOriginalFilename())
                        .mimeType(v.getMimeType())
                        .size(v.getFileSize())
                        .uploadedBy(v.getUploadedBy().getFullName())
                        .uploadedAt(v.getUploadedAt())
                        .build())
                .toList();
    }
}