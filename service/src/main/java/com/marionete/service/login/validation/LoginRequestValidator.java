package com.marionete.service.login.validation;

import org.springframework.stereotype.Service;
import services.LoginRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginRequestValidator {
    //The code should be converted to string using expected language
    private static final String USER_NAME_EMPTY  = "USER_NAME_EMPTY";
    private static final String PASSWORD_EMPTY  = "PASSWORD_EMPTY";
    public ValidationResponse validate(LoginRequest loginRequest){
        List<String> validationErrors = new ArrayList<String>();
        if(loginRequest.getUsername().isEmpty()){
            validationErrors.add(USER_NAME_EMPTY);
        }
        if(loginRequest.getPassword().isEmpty()){
            validationErrors.add(PASSWORD_EMPTY);
        }
        return new ValidationResponse(validationErrors.isEmpty(),validationErrors);
    }
}
