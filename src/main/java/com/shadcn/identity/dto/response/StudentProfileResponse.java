package com.shadcn.identity.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentProfileResponse extends UserProfileResponse {
    String studentId;

    String middleName;

    String gpa;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate enrollmentDate;

    String departmentId;

    String guardianName;

    String guardianPhoneNumber;

    String nationality;

    String religion;

    String degreeLevel;

    String academicYearId;

    String present;
}
