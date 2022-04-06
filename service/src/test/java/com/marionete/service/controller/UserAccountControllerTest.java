package com.marionete.service.controller;

import com.marionete.service.exception.TokenException;
import com.marionete.service.model.PostUserAccount;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.query.GetUserAccountQuery;
import com.marionete.service.query.GetUserAccountQueryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountControllerTest {
    @InjectMocks
    private UserAccountController userAccountController;

    @Mock
    private GetUserAccountQueryHandler getUserAccountQueryHandler;

    private final PodamFactory factory;

    UserAccountControllerTest() {
        factory = new PodamFactoryImpl();
    }

    @Test
    public void postUserAccountShouldReturnUserAccountForValidRequests() throws TokenException {
        // given
        UserAccount userAccount = factory.manufacturePojo(UserAccount.class);
        PostUserAccount postUserAccount = factory.manufacturePojo(PostUserAccount.class);

        when(getUserAccountQueryHandler.handle(any(GetUserAccountQuery.class))).thenReturn(Mono.just(userAccount));

        //when
        UserAccount result = userAccountController.userAccount(postUserAccount);
        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(userAccount);
    }

    @Test
    public void postUserAccountShouldFailForInvalidRequest() throws TokenException {
        // given
        PostUserAccount postUserAccount = new PostUserAccount();
        postUserAccount.setUsername("");
        postUserAccount.setPassword("");

        when(getUserAccountQueryHandler.handle(any(GetUserAccountQuery.class))).thenReturn(Mono.empty());

        //when
        UserAccount result = userAccountController.userAccount(postUserAccount);
        //then

    }
}