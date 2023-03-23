package com.example.gwallet.controller.RequestDTOs;

import com.example.gwallet.model.anotations.AddressExist;
import com.example.gwallet.model.anotations.AmountIsValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransactionRequestDTO {

    @AddressExist
    @NotNull
    @JsonProperty("sender_address")
    @Size(max = 42,message = "Wallet size must be 42 characters")
    private String senderAddress;
    @AddressExist
    @NotNull
    @JsonProperty("receiver_address")
    @Size(max = 42,message = "Wallet size must be 42 characters")
    private String receiverAddress;

    @AmountIsValid
    @NotNull(message = "Amount can not be null")
    private Double amount;

    public TransactionRequestDTO(String senderAddress, String receiverAddress, Double amount) {
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.amount = amount;
    }

    public TransactionRequestDTO() {
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public Double getAmount() {
        return amount;
    }
}
