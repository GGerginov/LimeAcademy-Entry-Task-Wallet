package com.example.gwallet.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Wallet {

    private UUID id;
    private String address;

    private Double balance = 100.00;

    private List<Transaction> sendTransactions;
    private List<Transaction> receiveTransactions;


    public Wallet() {
    }

    public Wallet(String address, Double balance) {
        this.address = address;
        this.balance = balance;
    }

    public Wallet(String address) {
        this.address = address;
    }

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true, length = 42)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(columnDefinition = "Decimal(60,30) default '100.00'")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @OneToMany(mappedBy = "sender")
    public List<Transaction> getSendTransactions() {
        return sendTransactions;
    }

    public void setSendTransactions(List<Transaction> sendTransactions) {
        this.sendTransactions = sendTransactions;
    }

    @OneToMany(mappedBy = "receiver")
    public List<Transaction> getReceiveTransactions() {
        return receiveTransactions;
    }

    public void setReceiveTransactions(List<Transaction> receiveTransactions) {
        this.receiveTransactions = receiveTransactions;
    }
}
