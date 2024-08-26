package com.shadcn.identity.dto.response;

import java.util.Date;
import java.util.Set;

import com.shadcn.identity.enums.Gender;
import com.shadcn.identity.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class UserResponse extends BaseDTO {

    String username;
    String firstName;
    String lastName;
    Date dateOfBirth;
    Gender gender;
    String phoneNumber;
    String address;
    String email;
    String avatar;
    Set<String> roles;
    Status status;
}
