package com.thanhtan.identity.dto.request;

import com.thanhtan.identity.enums.Status;
import com.thanhtan.identity.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 8, max = 20, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

    @Builder.Default
    Status status = Status.ACTIVE;
}
