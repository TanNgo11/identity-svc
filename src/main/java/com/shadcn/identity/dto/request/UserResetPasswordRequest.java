package com.shadcn.identity.dto.request;

import lombok.*;
import lombok.experimental.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResetPasswordRequest {

    String token;
    String newPassword;
}
