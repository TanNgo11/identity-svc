package com.thanhtan.identity.dto.request;

import com.thanhtan.identity.enums.Status;
import com.thanhtan.identity.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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

