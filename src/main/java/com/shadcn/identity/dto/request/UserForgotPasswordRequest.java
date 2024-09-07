package com.shadcn.identity.dto.request;

import java.time.*;

import com.shadcn.identity.enums.*;
import com.shadcn.identity.validator.*;

import lombok.*;
import lombok.experimental.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserForgotPasswordRequest {

    String username;
}
