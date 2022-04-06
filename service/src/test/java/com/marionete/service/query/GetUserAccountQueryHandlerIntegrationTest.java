package com.marionete.service.query;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.service.BeforeAllTestsExtension;
import com.marionete.service.exception.TokenException;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.query.GetUserAccountQuery;
import org.junit.jupiter.api.BeforeAll;
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
class GetUserAccountQueryHandlerIntegrationTest {
    private GetUserAccountQueryHandler getUserAccountQueryHandler;

    @Autowired
    GetUserAccountQueryHandlerIntegrationTest(GetUserAccountQueryHandler getUserAccountQueryHandler) {
        this.getUserAccountQueryHandler = getUserAccountQueryHandler;
    }

    @Test
    public void getReturnsUserAccountInfoForValidRequests() throws TokenException {
        //given
        GetUserAccountQuery query = new GetUserAccountQuery("VALID_USER","VALID_PASSWORD");
        //when
        UserAccount userAccount = getUserAccountQueryHandler.handle(query).block();
        //then
        assertThat(userAccount.getAccountInfo().getAccountNumber()).isEqualTo("12345-3346-3335-4456");
        assertThat(userAccount.getUserInfo().getName()).isEqualTo("John");
        assertThat(userAccount.getUserInfo().getSurname()).isEqualTo("Doe");
        assertThat(userAccount.getUserInfo().getAge()).isEqualTo(32);
        assertThat(userAccount.getUserInfo().getSex()).isEqualTo("male");

    }

}