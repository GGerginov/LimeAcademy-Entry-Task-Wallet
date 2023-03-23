package com.example.gwallet.exceptions;


import com.example.gwallet.model.jsonMessages.Messages.ErrorMessage;

public class ApiException extends Exception {

    private ErrorMessage message;

    public ApiException(ErrorMessage message) {
        super(message.getMessage());
        this.message = message;
    }

    public ErrorMessage getErrorMessage() {
        return message;
    }

}