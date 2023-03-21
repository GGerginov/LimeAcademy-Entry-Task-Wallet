package com.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction extends BaseEntity{



    private Wallet sender;


    private Wallet receiver;
    private Double amount;

    public Transaction() {
    }


    @Column(nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @ManyToOne
    @JoinColumn(name = "sender_id")
    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }
}
