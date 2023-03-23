package com.example.gwallet.controller.RequestDTOs;

import com.example.gwallet.model.anotations.AddressExist;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class TransactionRequestDTO {

    @AddressExist
    @JsonProperty("sender_address")
    @Size(max = 42,message = "//TODO")
    private String senderAddress;
    @AddressExist
    @JsonProperty("receiver_address")
    @Size(max = 42,message = "//TODO")
    private String receiverAddress;

    @Min(value = 0,message = "Amount can not be negative")
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
