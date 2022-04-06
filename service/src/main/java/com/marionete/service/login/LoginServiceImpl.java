package com.marionete.service.login;

import com.marionete.service.login.validation.LoginRequestValidator;
import com.marionete.service.login.validation.ValidationResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc.LoginServiceImplBase;

//This should be on its own service application
//Proto file should be in a separate module to be supplied to
//both the client and the server implementation
@GrpcService
public class LoginServiceImpl extends LoginServiceImplBase {

    private final LoginRequestValidator loginRequestValidator;

    @Autowired
    public LoginServiceImpl(LoginRequestValidator loginRequestValidator) {
        this.loginRequestValidator = loginRequestValidator;
    }

    @Override
    public void login(LoginRequest request,
                      StreamObserver<LoginResponse> responseObserver) {

        ValidationResponse validationResponse = loginRequestValidator.validate(request);
        //TODO: LoginResponse should include error We are returning empty token for error per contract
        String token = validationResponse.isValid() ? Integer.toString(request.hashCode()) : "";
        LoginResponse response = LoginResponse.newBuilder()
                .setToken(token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
