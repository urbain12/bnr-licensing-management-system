package com.bnr.backend.document.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DocumentResponse {
    private String documentType;
    private Integer versionNumber;
    private String filename;
    private String mimeType;
    private Long size;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
}