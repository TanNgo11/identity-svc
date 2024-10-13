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
    String firstName;
    String lastName;


    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;
    
    String address;
    String email;
    String phoneNumber;
    String gender;
}
