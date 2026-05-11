package com.bnr.backend.document.service;

import com.bnr.backend.applications.entity.Application;
import com.bnr.backend.document.entity.ApplicationDocument;
import com.bnr.backend.document.entity.ApplicationDocumentVersion;
import com.bnr.backend.document.entity.DocumentType;
import com.bnr.backend.document.repository.ApplicationDocumentRepository;
import com.bnr.backend.document.repository.ApplicationDocumentVersionRepository;
import com.bnr.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ApplicationDocumentRepository documentRepository;
    private final ApplicationDocumentVersionRepository versionRepository;

    @Value("${app.storage.root}")
    private String storageRoot;

    public void upload(Application app, DocumentType type, MultipartFile file, User actor) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(type + " is required");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File exceeds 5MB limit");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("application/pdf")
                        || contentType.equals("application/msword")
                        || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            throw new IllegalArgumentException("Only PDF/DOC/DOCX files are allowed");
        }

        try {
            ApplicationDocument document = documentRepository
                    .findByApplication_IdAndDocumentType(app.getId(), type)
                    .orElseGet(() -> documentRepository.save(
                            ApplicationDocument.builder()
                                    .id(UUID.randomUUID())
                                    .application(app)
                                    .documentType(type)
                                    .createdAt(LocalDateTime.now())
                                    .build()
                    ));

            int nextVersion = (int) versionRepository.countByDocument_Id(document.getId()) + 1;

            String originalName = file.getOriginalFilename();
            String storedName = UUID.randomUUID() + "_" + originalName;

            Path dir = Paths.get(storageRoot, app.getId().toString(), type.name());
            Files.createDirectories(dir);

            Path target = dir.resolve(storedName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            versionRepository.save(
                    ApplicationDocumentVersion.builder()
                            .id(UUID.randomUUID())
                            .document(document)
                            .versionNumber(nextVersion)
                            .originalFilename(originalName)
                            .storedFilename(storedName)
                            .filePath(target.toString())
                            .mimeType(contentType)
                            .fileSize(file.getSize())
                            .uploadedBy(actor)
                            .uploadedAt(LocalDateTime.now())
                            .build()
            );

        } catch (Exception e) {
            throw new IllegalStateException("Failed to store document");
        }
    }
}