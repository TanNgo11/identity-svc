package com.shadcn.identity.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private Pattern pattern;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isEmpty()) {
            return true; // This is handled by @NotNull annotation
        }
        return pattern.matcher(email).matches();
    }
}
