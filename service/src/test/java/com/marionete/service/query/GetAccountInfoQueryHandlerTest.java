package com.marionete.service.query;

import com.marionete.service.BeforeAllTestsExtension;
import com.marionete.service.model.AccountInfo;
import com.marionete.service.model.query.GetAccountInfoQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

//IntegrationTest with mock server
@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.login-server.address=in-process:test" // Configure the client to connect to the inProcess server
})
@ExtendWith({BeforeAllTestsExtension.class})
@DirtiesContext
class GetAccountInfoQueryHandlerTest {
    private final GetAccountInfoQueryHandler getAccountInfoQueryHandler;

    @Autowired
    GetAccountInfoQueryHandlerTest(GetAccountInfoQueryHandler getAccountInfoQueryHandler) {
        this.getAccountInfoQueryHandler = getAccountInfoQueryHandler;
    }

    @Test
    public void getReturnsAccountInfoForValidRequests(){
        //given
        GetAccountInfoQuery query = new GetAccountInfoQuery("VALID_TOKEN");
        //when
        AccountInfo accountInfo = getAccountInfoQueryHandler.handle(query).block();
        //then
        assertThat(accountInfo).isNotNull();
        assertThat(accountInfo.getAccountNumber()).isNotEmpty();
    }
}