package com.bnr.backend.audit.repository;

import com.bnr.backend.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByApplication_IdOrderByTimestampDesc(UUID applicationId);
}