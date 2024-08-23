package com.thanhtan.identity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thanhtan.identity.entity.Role;
import com.thanhtan.identity.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findAllByRolesContains(Role role);

    @Query("SELECT u FROM User u JOIN FETCH u.roles")
    List<User> findAllWithRoles();

    @Query("SELECT u FROM User u JOIN FETCH u.roles r WHERE r.name = :roleName")
    List<User> findAllWithRoles(@Param("roleName") String roleName);

    User findById(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findUsersByIds(List<Long> ids);
}
