package com.bnr.backend.roles.repository;

import com.bnr.backend.common.enums.PermissionType;
import com.bnr.backend.roles.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByName(PermissionType name);
}