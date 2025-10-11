package com.project.ptittoanthu.common.validator;

import com.project.ptittoanthu.common.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
    private String message;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !PATTERN.matcher(email).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ValidEmail  constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }
}
