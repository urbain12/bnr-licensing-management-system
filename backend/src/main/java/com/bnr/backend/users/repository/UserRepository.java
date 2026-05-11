package com.bnr.backend.users.repository;

import com.bnr.backend.common.enums.RoleType;
import com.bnr.backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByRoles_Name(RoleType role);

    @Query("""
        select u from User u
        join u.roles r
        where r.name = :role
        and u.active = true
    """)
    List<User> findActiveUsersByRole(RoleType role);

}