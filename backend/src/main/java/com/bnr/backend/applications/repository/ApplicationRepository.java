package com.bnr.backend.applications.repository;

import com.bnr.backend.applications.entity.Application;
import com.bnr.backend.applications.entity.ApplicationState;
import com.bnr.backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findBySubmittedBy(User user);

    List<Application> findByReviewer(User user);

    List<Application> findByApprover(User approver);

    long countByReviewer(User reviewer);

    long countByApprover(User approver);

    List<Application> findByStatus(ApplicationState status);
}