package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import com.shadcn.identity.enums.Status;
import com.shadcn.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdationRequest {
    String firstName;
    String lastName;
    String phoneNumber;
    String gender;
    String address;
    String email;
    Status status;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dateOfBirth;
}
