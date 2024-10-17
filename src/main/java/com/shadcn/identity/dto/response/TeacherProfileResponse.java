package com.shadcn.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shadcn.identity.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherProfileResponse extends UserProfileResponse {
    String teacherId;

    String grade;

    LocalDate enrollmentDate;

    String major;

    String emergencyContactName;

    String emergencyContactPhoneNumber;

    String email;

    String hireDate;

    String avatarPath;

    // 54 dân tộc :)))
    String nation;

    String religion;

    String citizenId;

    String present;

    String firstName;

    String lastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;

    String city;

    String phoneNumber;

    Gender gender;

    String address;

    String department;

    Double salary;

    String officeHours;

}
