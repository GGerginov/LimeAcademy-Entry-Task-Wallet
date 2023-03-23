package com.example.gwallet.model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WalletDto implements Serializable {

    private UUID id;

    private String address;
    private Double balance;
    private List<TransactionDto> sendTransactions;
    private List<TransactionDto> receiveTransactions;

    public WalletDto(String address) {
        this.address = address;
    }

    public WalletDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Double getBalance() {
        return balance;
    }

    @JsonIgnore
    public List<TransactionDto> getSendTransactions() {
        return sendTransactions;
    }

    @JsonIgnore
    public List<TransactionDto> getReceiveTransactions() {
        return receiveTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletDto entity = (WalletDto) o;
        return Objects.equals(this.address, entity.address) &&
                Objects.equals(this.balance, entity.balance) &&
                Objects.equals(this.sendTransactions, entity.sendTransactions) &&
                Objects.equals(this.receiveTransactions, entity.receiveTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, balance, sendTransactions, receiveTransactions);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "address = " + address + ", " +
                "balance = " + balance + ", " +
                "sendTransactions = " + sendTransactions + ", " +
                "receiveTransactions = " + receiveTransactions + ")";
    }
}
