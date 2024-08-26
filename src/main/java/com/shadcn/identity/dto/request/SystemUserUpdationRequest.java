package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.shadcn.identity.enums.Gender;
import com.shadcn.identity.enums.Status;
import com.shadcn.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemUserUpdationRequest {

    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;
    String phoneNumber;
    Gender gender;
    String address;
    String email;
    Status status;
    String roles;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dateOfBirth;
}