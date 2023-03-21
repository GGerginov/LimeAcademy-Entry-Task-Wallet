package com.example.gwallet.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Transaction {

    private UUID id;

    private Wallet sender;

    private Wallet receiver;

    private Double amount;

    public Transaction() {
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


    @Column(nullable = false, updatable = false, columnDefinition = "Decimal(60,30) default '100.00'")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false, updatable = false)
    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false, updatable = false)
    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }
}
