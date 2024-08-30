package com.shadcn.identity.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String userId;
    String firstName;
    String lastName;
    //@JsonSerialize(using = LocalDateSerializer.class)
    LocalDate dateOfBirth;
    String address;
    String email;
    String phoneNumber;
    String gender;
}
