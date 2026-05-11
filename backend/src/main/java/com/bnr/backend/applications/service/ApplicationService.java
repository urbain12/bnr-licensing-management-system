package com.bnr.backend.applications.service;

import com.bnr.backend.applications.dto.ApplicationResponse;
import com.bnr.backend.applications.dto.CreateApplicationRequest;
import com.bnr.backend.applications.entity.Application;
import com.bnr.backend.applications.entity.ApplicationState;
import com.bnr.backend.applications.repository.ApplicationRepository;
import com.bnr.backend.audit.entity.AuditLog;
import com.bnr.backend.audit.repository.AuditRepository;
import com.bnr.backend.common.enums.RoleType;
import com.bnr.backend.document.entity.DocumentType;
import com.bnr.backend.document.service.DocumentService;
import com.bnr.backend.security.CurrentUserService;
import com.bnr.backend.users.entity.User;
import com.bnr.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AuditRepository auditRepository;
    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;

    private final DocumentService documentService;

    @Transactional
    public ApplicationResponse createWithDocuments(
            String institutionName,
            String licenseType,
            String businessAddress,
            MultipartFile marketRisk,
            MultipartFile clientAsset,
            MultipartFile tradingPolicy
    ) {
        User actor = currentUserService.getCurrentUser();

        User assignedReviewer = findReviewerWithLowestWorkload();
        User assignedApprover = findApproverWithLowestWorkload();

        Application app = Application.builder()
                .id(UUID.randomUUID())
                .referenceNumber("REF-" + System.currentTimeMillis())
                .institutionName(institutionName)
                .licenseType(licenseType)
                .businessAddress(businessAddress)
                .status(ApplicationState.SUBMITTED)
                .submittedBy(actor)
                .reviewer(assignedReviewer)
                .approver(assignedApprover)
                .submittedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Application saved = applicationRepository.save(app);

        documentService.upload(saved, DocumentType.MARKET_RISK_MANAGEMENT_POLICY, marketRisk, actor);
        documentService.upload(saved, DocumentType.CLIENT_ASSET_PROTECTION_POLICY, clientAsset, actor);
        documentService.upload(saved, DocumentType.TRADING_POLICY, tradingPolicy, actor);

        audit(saved, actor, "SUBMITTED_APPLICATION", null, "SUBMITTED", null);

        audit(saved, actor, "APPLICATION_ASSIGNED",
                "SUBMITTED",
                "SUBMITTED",
                "Reviewer: " + assignedReviewer.getFullName()
                        + ", Approver: " + assignedApprover.getFullName());

        return toResponse(saved);
    }

    private User findReviewerWithLowestWorkload() {
        List<User> reviewers = userRepository.findActiveUsersByRole(RoleType.REVIEWER);

        if (reviewers.isEmpty()) {
            throw new IllegalStateException("No active reviewer available");
        }

        return reviewers.stream()
                .min((a, b) -> Long.compare(
                        applicationRepository.countByReviewer(a),
                        applicationRepository.countByReviewer(b)
                ))
                .orElseThrow();
    }

    private User findApproverWithLowestWorkload() {
        List<User> approvers = userRepository.findActiveUsersByRole(RoleType.APPROVER);

        if (approvers.isEmpty()) {
            throw new IllegalStateException("No active approver available");
        }

        return approvers.stream()
                .min((a, b) -> Long.compare(
                        applicationRepository.countByApprover(a),
                        applicationRepository.countByApprover(b)
                ))
                .orElseThrow();
    }

    public List<ApplicationResponse> findAll() {
        User actor = currentUserService.getCurrentUser();

        if (actor.hasRole("ADMIN")) {
            return applicationRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (actor.hasRole("APPLICANT")) {
            return applicationRepository.findBySubmittedBy(actor)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (actor.hasRole("REVIEWER")) {
            return applicationRepository.findByReviewer(actor)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (actor.hasRole("APPROVER")) {
            return applicationRepository.findByApprover(actor)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return List.of();
    }

    public ApplicationResponse findById(UUID id) {
        return toResponse(getApplication(id));
    }

    public Application getApplication(UUID id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    public ApplicationResponse toResponse(Application app) {
        return ApplicationResponse.builder()
                .id(app.getId())
                .name(app.getInstitutionName())
                .referenceNumber(app.getReferenceNumber())
                .licenseType(app.getLicenseType())
                .status(app.getStatus())
                .build();
    }

    public void audit(
            Application app,
            User actor,
            String action,
            String previous,
            String next,
            String comment
    ) {
        auditRepository.save(AuditLog.builder()
                .id(UUID.randomUUID())
                .application(app)
                .actor(actor)
                .action(action)
                .previousState(previous)
                .newState(next)
                .timestamp(LocalDateTime.now())
                .comment(comment)
                .build());
    }
}