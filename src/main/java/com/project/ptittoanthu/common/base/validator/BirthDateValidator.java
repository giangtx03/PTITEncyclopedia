package com.project.ptittoanthu.common.base.validator;

import com.project.ptittoanthu.common.base.annotation.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {
    private static final int MIN_AGE = 15;

    private String message;

    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false;
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < MIN_AGE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
