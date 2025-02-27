package com.shadcn.identity.dto.response;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shadcn.identity.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    Long id;
    String firstName;
    String lastName;
    String username;

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;

    String address;
    String email;
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String avatarPath;

    Set<String> roles;
}
