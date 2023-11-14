package com.audition.validation;

import com.google.common.base.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<IntegerConstraint, String> {

    private boolean isMandatory;

    @Override
    public void initialize(IntegerConstraint constraint) {
        isMandatory = constraint.mandatory();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNullOrEmpty(value)) {
            return handleEmpty(value, context);
        }
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean handleEmpty(String value, ConstraintValidatorContext context) {
        if (isMandatory) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mandatory input not available").addConstraintViolation();
            return false;
        }
        return true;
    }
}
