package com.bnr.backend.roles.repository;

import com.bnr.backend.common.enums.RoleType;
import com.bnr.backend.roles.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleType name);
}