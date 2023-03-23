package com.example.gwallet.controller.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TransactionResponseDTO {

    @JsonProperty("transaction_id")
    private UUID id;

    @JsonProperty("transaction_amount")
    private Double amount;

    @JsonProperty("sender_address")
    private String senderAddress;

    @JsonProperty("receiver_address")
    private String receiverAddress;


    public TransactionResponseDTO(UUID id, Double amount, String senderAddress, String receiverAddress) {
        this.id = id;
        this.amount = amount;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }


    public TransactionResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }
}
