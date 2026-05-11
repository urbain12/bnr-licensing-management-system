package com.bnr.backend.workflow.service;

import com.bnr.backend.applications.dto.ApplicationResponse;
import com.bnr.backend.applications.entity.Application;
import com.bnr.backend.applications.entity.ApplicationState;
import com.bnr.backend.applications.repository.ApplicationRepository;
import com.bnr.backend.applications.service.ApplicationService;
import com.bnr.backend.security.CurrentUserService;
import com.bnr.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bnr.backend.applications.entity.ApplicationState.*;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public ApplicationResponse startReview(UUID id) {
        User actor = currentUserService.getCurrentUser();
        Application app = applicationService.getApplication(id);

        ApplicationState previous = app.getStatus();

        if (previous == SUBMITTED || previous == INFO_REQUESTED || previous == RESUBMITTED) {
            app.setStatus(UNDER_REVIEW);
        } else {
            throw new IllegalStateException("Invalid transition from " + previous + " to UNDER_REVIEW");
        }

        app.setReviewer(actor);
        app.setUpdatedAt(LocalDateTime.now());

        Application saved = applicationRepository.save(app);

        applicationService.audit(saved, actor, "START_REVIEW", previous.name(), UNDER_REVIEW.name(), null);

        return applicationService.toResponse(saved);
    }

    @Transactional
    public ApplicationResponse completeReview(UUID id, String comment) {
        User actor = currentUserService.getCurrentUser();
        Application app = applicationService.getApplication(id);

        transition(app, UNDER_REVIEW, REVIEW_COMPLETED);

        if (!app.getReviewer().getId().equals(actor.getId())) {
            throw new IllegalStateException("Application is not assigned to this reviewer");
        }

        Application saved = applicationRepository.save(app);

        applicationService.audit(saved, actor, "MARK_REVIEWED", UNDER_REVIEW.name(), REVIEW_COMPLETED.name(), comment);

        return applicationService.toResponse(saved);
    }

    @Transactional
    public ApplicationResponse requestInfo(UUID id, String comment) {
        User actor = currentUserService.getCurrentUser();
        Application app = applicationService.getApplication(id);

        ApplicationState previous = app.getStatus();

        if (previous != UNDER_REVIEW && previous != SUBMITTED) {
            throw new IllegalStateException("Information can only be requested during review");
        }

        app.setStatus(INFO_REQUESTED);
        app.setReviewer(actor);
        app.setUpdatedAt(LocalDateTime.now());

        Application saved = applicationRepository.save(app);

        applicationService.audit(saved, actor, "REQUEST_INFORMATION", previous.name(), INFO_REQUESTED.name(), comment);

        return applicationService.toResponse(saved);
    }

    @Transactional
    public ApplicationResponse approve(UUID id, String comment) {
        User actor = currentUserService.getCurrentUser();
        Application app = applicationService.getApplication(id);

        transition(app, REVIEW_COMPLETED, APPROVED);

        if (app.getReviewer() != null && app.getReviewer().getId().equals(actor.getId())) {
            throw new IllegalStateException("Reviewer cannot approve the same application");
        }

        if (!app.getApprover().getId().equals(actor.getId())) {
            throw new IllegalStateException("Application is not assigned to this approver");
        }

        Application saved = applicationRepository.save(app);

        applicationService.audit(saved, actor, "APPROVED_APPLICATION", REVIEW_COMPLETED.name(), APPROVED.name(), comment);

        return applicationService.toResponse(saved);
    }

    @Transactional
    public ApplicationResponse reject(UUID id, String comment) {
        User actor = currentUserService.getCurrentUser();
        Application app = applicationService.getApplication(id);

        transition(app, REVIEW_COMPLETED, REJECTED);

        if (app.getReviewer() != null && app.getReviewer().getId().equals(actor.getId())) {
            throw new IllegalStateException("Reviewer cannot reject the same application");
        }

        if (!app.getApprover().getId().equals(actor.getId())) {
            throw new IllegalStateException("Application is not assigned to this approver");
        }

        Application saved = applicationRepository.save(app);

        applicationService.audit(saved, actor, "REJECTED_APPLICATION", REVIEW_COMPLETED.name(), REJECTED.name(), comment);

        return applicationService.toResponse(saved);
    }

    private void transition(Application app, ApplicationState expected, ApplicationState next) {
        if (app.getStatus() == APPROVED || app.getStatus() == REJECTED) {
            throw new IllegalStateException("Final decision cannot be changed");
        }

        if (app.getStatus() != expected) {
            throw new IllegalStateException(
                    "Invalid transition from " + app.getStatus() + " to " + next
            );
        }

        app.setStatus(next);
        app.setUpdatedAt(LocalDateTime.now());
    }
}