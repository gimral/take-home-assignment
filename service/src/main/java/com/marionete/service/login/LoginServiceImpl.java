package com.marionete.service.login;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc.LoginServiceImplBase;

//This should be on its own service application
//Proto file should be in a seperate module to be supplied to
//both the client and the server implementation
@GrpcService
public class LoginServiceImpl extends LoginServiceImplBase {

//    @Autowired
//    private ValidationService validationService;

//    @GrpcAdvice
//    public class CityScoreExceptionHandler {
//
    @Override
    public void login(LoginRequest request,
                      StreamObserver<LoginResponse> responseObserver) {

        LoginResponse response = LoginResponse.newBuilder()
                .setToken(Integer.toString(request.hashCode()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
