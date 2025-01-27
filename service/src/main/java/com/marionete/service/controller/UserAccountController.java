package com.marionete.service.controller;

import com.marionete.service.exception.TokenException;
import com.marionete.service.model.PostUserAccount;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.query.GetUserAccountQuery;
import com.marionete.service.query.GetUserAccountQueryHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserAccountController {

    private final GetUserAccountQueryHandler getUserAccountQueryHandler;

    @Autowired
    public UserAccountController(GetUserAccountQueryHandler getUserAccountQueryHandler) {
        this.getUserAccountQueryHandler = getUserAccountQueryHandler;
    }

    //TokenException is configured to return UnAuthorized Error in exception implementation
    @PostMapping("/marionete/useraccount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserAccount returned",content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccount.class))),
            @ApiResponse(responseCode = "400", description = "Validation Failed",content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401",content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))})
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserAccount> userAccount(@Valid @RequestBody PostUserAccount postUserAccount) throws TokenException {
        GetUserAccountQuery query = new GetUserAccountQuery(postUserAccount.getUsername(),
                postUserAccount.getPassword());
        return getUserAccountQueryHandler.handle(query);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
