package com.marionete.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.service.BeforeAllTestsExtension;
import com.marionete.service.exception.TokenException;
import com.marionete.service.model.PostUserAccount;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.query.GetUserAccountQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.login-server.address=in-process:test" // Configure the client to connect to the inProcess server
})
@AutoConfigureMockMvc
@ExtendWith({BeforeAllTestsExtension.class})
@DirtiesContext
public class UserAccountControllerMockMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private final PodamFactory factory;

    @Autowired
    public UserAccountControllerMockMvcTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        factory = new PodamFactoryImpl();
    }

    @Test
    public void postUserAccountShouldFailAndReturns400ForInvalidRequest() throws Exception {
        // given
        PostUserAccount postUserAccount = new PostUserAccount();

        //when
        MvcResult result =mockMvc.perform(post("/marionete/useraccount")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postUserAccount)))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).contains("password");
    }

    @Test
    public void postUserAccountShouldReturnUserAccountForValidRequests() throws Exception {
        // given
        PostUserAccount postUserAccount = factory.manufacturePojo(PostUserAccount.class);
        //when
        MvcResult asyncResult =mockMvc.perform(post("/marionete/useraccount")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postUserAccount)))
                .andReturn();

        MvcResult result = mockMvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isOk())
                .andReturn();
        //then
        UserAccount userAccount = objectMapper.readValue(result.getResponse().getContentAsString(), UserAccount.class);
        Assertions.assertThat(userAccount.getAccountInfo().getAccountNumber()).isEqualTo("12345-3346-3335-4456");
        Assertions.assertThat(userAccount.getUserInfo().getName()).isEqualTo("John");
        Assertions.assertThat(userAccount.getUserInfo().getSurname()).isEqualTo("Doe");
        Assertions.assertThat(userAccount.getUserInfo().getAge()).isEqualTo(32);
        Assertions.assertThat(userAccount.getUserInfo().getSex()).isEqualTo("male");
    }
}
