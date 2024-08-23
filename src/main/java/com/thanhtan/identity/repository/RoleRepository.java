package com.thanhtan.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhtan.identity.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
