package com.marionete.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Not sure if it is a problem with credentials because we dont have the granular error
//But lets assume it is
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Exception while acquiring token")
public class TokenException extends Exception {
}
