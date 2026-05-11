package com.bnr.backend.document.repository;

import com.bnr.backend.document.entity.ApplicationDocument;
import com.bnr.backend.document.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, UUID> {

    Optional<ApplicationDocument> findByApplication_IdAndDocumentType(UUID applicationId, DocumentType documentType);
}

