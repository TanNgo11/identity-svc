package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import com.shadcn.identity.validator.EmailConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.shadcn.identity.enums.Status;
import com.shadcn.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @EmailConstraint
    String email;

    @Builder.Default
    Status status = Status.ACTIVE;

    String address;
    String gender;
    String phoneNumber;
}
