package com.shadcn.identity.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ResetPasswordToken extends BaseEntity {
    String token;
    LocalDateTime expiryDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
