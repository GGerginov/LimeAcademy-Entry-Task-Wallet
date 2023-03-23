package com.example.gwallet.model.jsonMessages.Messages;

public class ErrorMessages {

    public static final ErrorMessage WALLET_NOT_FOUND = new ErrorMessage(404,"Wallet not found");

    public static final ErrorMessage TRANSACTION_FAILED = new ErrorMessage(400,"Transaction failed");
    public static final ErrorMessage SENDER_DOES_NOT_HAVE_ENOUGH_BALANCE = new ErrorMessage(400,"Sender does not have enough balance to make the transaction");
    public static final ErrorMessage INVALID_JSON_STRUCTURE = new ErrorMessage(400, "Invalid JSON structure.");

}
