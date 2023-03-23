package com.example.gwallet.model.jsonMessages.Messages;

public class ErrorMessage {

    private int code;
    private String message;

    public ErrorMessage(String message) {
        this.code = 400;
        this.message = message;
    }

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"code\": " + code + ",\n" +
                "\"message\": \"" + message + "\"\n" +
                '}';
    }
}
