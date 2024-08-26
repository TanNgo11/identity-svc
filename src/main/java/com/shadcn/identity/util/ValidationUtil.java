package com.shadcn.identity.util;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shadcn.identity.dto.request.CreateSystemUserRequest;
import com.shadcn.identity.dto.request.SystemUserUpdationRequest;
import com.shadcn.identity.dto.request.UserUpdationRequest;
import com.shadcn.identity.exception.AppException;
import com.shadcn.identity.exception.ErrorCode;

public class ValidationUtil {

    public static UserUpdationRequest validateUserStr(String userStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        UserUpdationRequest updateUserRequest = objectMapper.readValue(userStr, UserUpdationRequest.class);
        Set<ConstraintViolation<UserUpdationRequest>> violations = validator.validate(updateUserRequest);

        if (!violations.isEmpty()) {
            String errorMessage =
                    violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            ErrorCode errorCode = determineErrorCode(errorMessage);
            throw new AppException(errorCode);
        }

        return updateUserRequest;
    }

    public static CreateSystemUserRequest validateSystemUserStr(String userStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        CreateSystemUserRequest userRequest = objectMapper.readValue(userStr, CreateSystemUserRequest.class);
        Set<ConstraintViolation<CreateSystemUserRequest>> violations = validator.validate(userRequest);

        if (!violations.isEmpty()) {
            String errorMessage =
                    violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            ErrorCode errorCode = determineErrorCode(errorMessage);
            throw new AppException(errorCode);
        }

        return userRequest;
    }

    public static SystemUserUpdationRequest validateUpdateSystemUserStr(String userStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        SystemUserUpdationRequest userRequest = objectMapper.readValue(userStr, SystemUserUpdationRequest.class);
        Set<ConstraintViolation<SystemUserUpdationRequest>> violations = validator.validate(userRequest);

        if (!violations.isEmpty()) {
            String errorMessage =
                    violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            ErrorCode errorCode = determineErrorCode(errorMessage);
            throw new AppException(errorCode);
        }

        return userRequest;
    }

    private static ErrorCode determineErrorCode(String errorMessage) {

        if (errorMessage.contains("USERNAME_INVALID")) {
            return ErrorCode.USERNAME_INVALID;
        } else if (errorMessage.contains("INVALID_PASSWORD")) {
            return ErrorCode.INVALID_PASSWORD;
        } else if (errorMessage.contains("INVALID_DOB")) {
            return ErrorCode.INVALID_DOB;
        } else {
            return ErrorCode.INVALID_KEY;
        }
    }
}
