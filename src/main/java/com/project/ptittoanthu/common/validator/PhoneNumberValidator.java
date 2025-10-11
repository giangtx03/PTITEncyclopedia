package com.project.ptittoanthu.common.validator;

import com.project.ptittoanthu.common.annotation.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_REGEX = "^0\\d{2,10}$";
    private static final Pattern PATTERN = Pattern.compile(PHONE_REGEX);
    private String message;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || !PATTERN.matcher(phoneNumber).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
