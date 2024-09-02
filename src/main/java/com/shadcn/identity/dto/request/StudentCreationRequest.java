package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import com.shadcn.identity.enums.Role;
import jakarta.validation.constraints.Size;

import com.shadcn.identity.enums.Status;
import com.shadcn.identity.validator.DobConstraint;
import com.shadcn.identity.validator.EmailConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {
    @Size(min = 8, max = 20, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dateOfBirth;

    @EmailConstraint
    String email;

    @Builder.Default
    Status status = Status.ACTIVE;

    String address;
    String gender;
    String phoneNumber;

    @Builder.Default
    Role role = Role.STUDENT;
}