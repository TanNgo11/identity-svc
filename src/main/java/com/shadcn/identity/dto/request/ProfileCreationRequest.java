package com.shadcn.identity.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String userId;

    String username;

    String email;

    String firstName;

    String lastName;

    String address;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;

    String phoneNumber;

    String gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate enrollmentDate;

    String departmentId;

    String guardianName;

    String guardianPhoneNumber;

    String nationality;

    String religion;

    String degreeLevel;

    String academicYearId;

    Double salary;

    String emergencyContactName;

    String emergencyContactPhoneNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate hireDate;

    String officeHours;

    String workSchedule;
}
