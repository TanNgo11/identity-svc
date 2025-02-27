package com.shadcn.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shadcn.identity.enums.Gender;
import com.shadcn.identity.validator.DobConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
  String oldPassword;
  String newPassword;
}
