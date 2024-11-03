package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shadcn.identity.enums.Role;
import com.shadcn.identity.enums.Status;
import com.shadcn.identity.validator.EmailConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherCreationRequest {
    @Size(min = 8, max = 20, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;

    @EmailConstraint
    String email;

    @Builder.Default
    Status status = Status.ACTIVE;

    @Builder.Default
    Role role = Role.TEACHER;

    // Profile information
    String firstName;

    String lastName;

    String address;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;

    String phoneNumber;

    String gender;

    String departmentId;

    Double salary;

    String emergencyContactName;

    String emergencyContactPhoneNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate hireDate;

    String officeHours;
}
