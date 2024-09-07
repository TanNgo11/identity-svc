package com.shadcn.identity.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.shadcn.identity.entity.*;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    ResetPasswordToken findByToken(String token);

    ResetPasswordToken findByUserId(Long userId);
}
