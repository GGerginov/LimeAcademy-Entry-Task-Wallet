package com.example.gwallet.model.DTOs;

import com.example.gwallet.model.entity.Wallet;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link Wallet} entity
 */
public class WalletDto implements Serializable {
    private final UUID id;
    private final String address;
    private final Double balance;

    public WalletDto(UUID id, String address, Double balance) {
        this.id = id;
        this.address = address;
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletDto entity = (WalletDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.balance, entity.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, balance);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "address = " + address + ", " +
                "balance = " + balance + ")";
    }
}