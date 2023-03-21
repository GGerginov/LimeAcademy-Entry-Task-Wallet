package com.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Wallet extends BaseEntity{

    private String address;

    private Double balance;

    private List<Transaction> sendTransactions;
    private List<Transaction> receiveTransactions;
    public Wallet() {
    }


    @Column(nullable = false,unique = true,length = 42)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(nullable = false,columnDefinition="Decimal(10,2) default '100.00'")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @OneToMany(mappedBy="sender")
    public List<Transaction> getSendTransactions() {
        return sendTransactions;
    }

    public void setSendTransactions(List<Transaction> sendTransactions) {
        this.sendTransactions = sendTransactions;
    }

    @OneToMany(mappedBy="receiver")
    public List<Transaction> getReceiveTransactions() {
        return receiveTransactions;
    }

    public void setReceiveTransactions(List<Transaction> receiveTransactions) {
        this.receiveTransactions = receiveTransactions;
    }
}
