package com.marinho.event.driven.example.notificationsapp.transfer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class TransferMoneyDetails implements Serializable {

    private final String customerId;
    private final String originAccountNumber;
    private final String destinationAccountNumber;
    private int amount;
    private boolean externalBank;

    @JsonCreator
    public TransferMoneyDetails(String customerId, String originAccountNumber, String destinationAccountNumber, int amount, boolean externalBank) {
        this.customerId = customerId;
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.externalBank = externalBank;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isExternalBank() {
        return externalBank;
    }
}
