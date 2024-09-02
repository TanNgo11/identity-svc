package com.shadcn.identity.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String userId;
    String firstName;
    String lastName;

    @JsonSerialize(using = LocalDateSerializer.class)
    LocalDate dateOfBirth;

    String address;
    String email;
    String phoneNumber;
    String gender;
}
