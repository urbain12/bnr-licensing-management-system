package com.bnr.backend.document.repository;

import com.bnr.backend.document.entity.ApplicationDocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationDocumentVersionRepository extends JpaRepository<ApplicationDocumentVersion, UUID> {

    List<ApplicationDocumentVersion> findByDocument_Application_IdOrderByUploadedAtDesc(UUID applicationId);

    long countByDocument_Id(UUID documentId);
}