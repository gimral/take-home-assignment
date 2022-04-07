package com.marionete.service.login;

import com.marionete.service.login.validation.LoginRequestValidator;
import com.marionete.service.login.validation.ValidationResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.LoginRequest;
import services.LoginResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {
    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private LoginRequestValidator loginRequestValidator;


    @Test
    public void loginShouldReturnNonEmptyTokenForValidRequests() throws Exception {
        //given
        LoginRequest validRequest = LoginRequest.newBuilder()
                .setUsername("validUser")
                .setPassword("validPassword")
                .build();
        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        when(loginRequestValidator.validate(validRequest))
                .thenReturn(new ValidationResponse(true, new ArrayList<>()));

        //when
        loginService.login(validRequest,responseObserver);

        //then

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<LoginResponse> results = responseObserver.getValues();
        assertThat(results.size()).isEqualTo(1);
        LoginResponse response = results.get(0);
        assertThat(response.getToken()).isNotEmpty();
    }

    @Test
    public void loginShouldReturnEmptyTokenForValidRequests() throws Exception {
        //given
        LoginRequest validRequest = LoginRequest.newBuilder()
                .setUsername("")
                .setPassword("")
                .build();
        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        when(loginRequestValidator.validate(validRequest))
                .thenReturn(new ValidationResponse(false, new ArrayList<>()));

        //when
        loginService.login(validRequest,responseObserver);

        //then

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<LoginResponse> results = responseObserver.getValues();
        assertThat(results.size()).isEqualTo(1);
        LoginResponse response = results.get(0);
        assertThat(response.getToken()).isEmpty();
    }
}