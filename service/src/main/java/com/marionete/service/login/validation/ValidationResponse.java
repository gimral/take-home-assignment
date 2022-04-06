package com.marionete.service.login.validation;

import java.util.List;

public class ValidationResponse {
    private boolean valid;
    private List<String> validationErrors;

    public ValidationResponse(boolean valid, List<String> validationErrors) {
        this.valid = valid;
        this.validationErrors = validationErrors;
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
