package com.marionete.service.query;

import com.marionete.service.model.query.GetTokenQuery;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Service
public class GetTokenQueryHandler {
    //TODO: Could use future to support async calls
    @GrpcClient("login-server")
    private LoginServiceGrpc.LoginServiceBlockingStub loginServiceStub;

    public String handle(GetTokenQuery query){
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername(query.getUserName())
                .setPassword(query.getPassword())
                .build();

        LoginResponse response = loginServiceStub.login(loginRequest);
        //TODO: Handle Failures
        return response.getToken();
    }
}
