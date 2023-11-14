package com.audition.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AuditionPostRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "100"})
    void testValid(String input) {
        assertTrue(validator.validate(
            AuditionPostRequest.builder().userId(input).build()).isEmpty());
    }

    @Test
    void testValidWithoutUserId() {
        assertTrue(validator.validate(
            AuditionPostRequest.builder().build()).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", " ", "someText"})
    void testInvalid(String input) {
        Set<ConstraintViolation<AuditionPostRequest>> violations = validator.validate(
            AuditionPostRequest.builder().userId(input).build());
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid input. Not a valid number");
        ;
    }


}
