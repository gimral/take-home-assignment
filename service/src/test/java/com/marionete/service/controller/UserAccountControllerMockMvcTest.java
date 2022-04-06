package com.marionete.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.service.model.PostUserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerMockMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserAccountControllerMockMvcTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
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
}
