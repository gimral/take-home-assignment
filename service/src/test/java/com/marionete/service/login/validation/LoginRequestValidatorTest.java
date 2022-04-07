package com.marionete.service.login.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import services.LoginRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class LoginRequestValidatorTest {

    @InjectMocks
    private LoginRequestValidator loginRequestValidator;

    @Test
    public void validateFailsForEmptyUserName() {
        //given
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername("")
                .setPassword("NotEmpty")
                .build();
        //when
        ValidationResponse validationResponse = loginRequestValidator.validate(loginRequest);
        //then
        assertFalse(validationResponse.isValid());
        assertThat(validationResponse.getValidationErrors().size()).isEqualTo(1);
    }

    @Test
    public void validateFailsForEmptyPassword() {
        //given
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername("NotEmpty")
                .setPassword("")
                .build();
        //when
        ValidationResponse validationResponse = loginRequestValidator.validate(loginRequest);
        //then
        assertFalse(validationResponse.isValid());
        assertThat(validationResponse.getValidationErrors().size()).isEqualTo(1);
    }

    @Test
    public void validateFailsForEmptyUserNameAndPassword() {
        //given
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername("")
                .setPassword("")
                .build();
        //when
        ValidationResponse validationResponse = loginRequestValidator.validate(loginRequest);
        //then
        assertFalse(validationResponse.isValid());
        assertThat(validationResponse.getValidationErrors().size()).isEqualTo(2);
    }

    @Test
    public void validateSucceedsForNonEmptyUserNameAndPassword() {
        //given
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername("NotEmpty")
                .setPassword("NotEmpty")
                .build();
        //when
        ValidationResponse validationResponse = loginRequestValidator.validate(loginRequest);
        //then
        assertTrue(validationResponse.isValid());
        assertThat(validationResponse.getValidationErrors().size()).isEqualTo(0);
    }
}