package com.audition.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class IntegerValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "1000"})
    void testSuccess(String input) {
        assertTrue(validator.validate(
            SomeTest.builder().someField(input).build()).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmptyFailure(String input) {
        Set<ConstraintViolation<SomeTest>> violations = validator.validate(
            SomeTest.builder().someField(input).build());
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Mandatory input not available");
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", " ", "someText"})
    void testFailure(String input) {
        Set<ConstraintViolation<SomeTest>> violations = validator.validate(
            SomeTest.builder().someField(input).build());
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid input. Not a valid number");
    }

    @Test
    void testSuccessMandatoryTrue() {
        Set<ConstraintViolation<SomeTest>> violations = validator.validate(
            SomeTest.builder().build());
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Mandatory input not available");
    }

    @Test
    void testSuccessMandatoryFalse() {
        assertTrue(validator.validate(
            SomeOtherTest.builder().build()).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "1000"})
    @NullAndEmptySource
    void testValidInputMandatoryFalse(String input) {
        assertTrue(validator.validate(
            SomeOtherTest.builder().someField(input).build()).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", " ", "someText"})
    void testInvalidInputMandatoryFalse(String input) {
        Set<ConstraintViolation<SomeOtherTest>> violations = validator.validate(
            SomeOtherTest.builder().someField(input).build());
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid input. Not a valid number");
    }

    @Builder
    static class SomeTest {

        @IntegerConstraint
        private String someField;
    }

    @Builder
    static class SomeOtherTest {

        @IntegerConstraint(mandatory = false)
        private String someField;
    }
}
