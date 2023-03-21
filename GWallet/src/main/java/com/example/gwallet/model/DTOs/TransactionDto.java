package com.example.gwallet.model.DTOs;

import com.example.gwallet.model.entity.Transaction;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link Transaction} entity
 */
public class TransactionDto implements Serializable {
    private final UUID id;
    private final Double amount;
    private final WalletDto sender;
    private final WalletDto receiver;

    public TransactionDto(UUID id, Double amount, WalletDto sender, WalletDto receiver) {
        this.id = id;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    public UUID getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public WalletDto getSender() {
        return sender;
    }

    public WalletDto getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto entity = (TransactionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.amount, entity.amount) &&
                Objects.equals(this.sender, entity.sender) &&
                Objects.equals(this.receiver, entity.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, sender, receiver);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "amount = " + amount + ", " +
                "sender = " + sender + ", " +
                "receiver = " + receiver + ")";
    }
}