package com.marionete.service.query;

import com.marionete.service.BeforeAllTestsExtension;
import com.marionete.service.model.query.GetTokenQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//IntegrationTest
@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.login-server.address=in-process:test" // Configure the client to connect to the inProcess server
})
class GetTokenQueryHandlerTest {

    private GetTokenQueryHandler getTokenQueryHandler;

    @Autowired
    GetTokenQueryHandlerTest(GetTokenQueryHandler getTokenQueryHandler) {
        this.getTokenQueryHandler = getTokenQueryHandler;
    }

    @Test
    public void getReturnsValidToken(){
        //given
        GetTokenQuery getTokenQuery = new GetTokenQuery("VALID_USER","VALID_PASSWORD");
        //when
        String token = getTokenQueryHandler.handle(getTokenQuery);
        //then
        assertThat(token).isNotEmpty();
    }
}