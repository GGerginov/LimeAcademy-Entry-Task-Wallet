package com.example.gwallet.model.jsonMessages.ErrorResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseBody<T> {

    @JsonProperty("success")
    private boolean isSuccess;
    private T errors;

    protected ErrorResponseBody() {
    }

    public ErrorResponseBody(T error) {
        this.isSuccess = false;
        this.errors = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T getErrors() {
        return errors;
    }

}
