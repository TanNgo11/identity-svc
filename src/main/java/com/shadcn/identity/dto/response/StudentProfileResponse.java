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

    String grade;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate enrollmentDate;

    String major;

    String guardianName;

    String guardianPhoneNumber;

    String email;

    String avatarPath;
    // 54 dân tộc :)))
    String nation;

    String religion;

    String citizenId;
    // At the moment just have Information technology and Business Administration
    String faculty;
    // Ex: Đại học chính quy Tiếng Việt K10
    String degreeLevel;

    String schoolYear;

    String present;
}
