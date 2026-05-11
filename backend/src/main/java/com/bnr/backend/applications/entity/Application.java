package com.bnr.backend.applications.entity;

import com.bnr.backend.common.enums.ApplicationStatus;
import com.bnr.backend.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    private UUID id;

    @Column(name = "reference_number", nullable = false, unique = true)
    private String referenceNumber;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "license_type")
    private String licenseType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationState status;

    @Column(name = "business_address")
    private String businessAddress;

    @ManyToOne
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}