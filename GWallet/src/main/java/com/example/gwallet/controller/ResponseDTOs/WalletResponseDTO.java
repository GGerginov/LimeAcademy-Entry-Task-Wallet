package com.example.gwallet.controller.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletResponseDTO {
    @JsonProperty("wallet_address")
    private String address;

    @JsonProperty("wallet_balance")
    private Double balance;

    public WalletResponseDTO(String address, Double balance) {
        this.address = address;
        this.balance = balance;
    }

    public WalletResponseDTO() {
    }

    public String getAddress() {
        return address;
    }

    public Double getBalance() {
        return balance;
    }
}
