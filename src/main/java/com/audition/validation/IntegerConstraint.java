package com.audition.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = IntegerValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerConstraint {

    String message() default "Invalid input. Not a valid number";

    boolean mandatory() default true;

    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};

}
