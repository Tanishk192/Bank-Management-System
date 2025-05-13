package com.banksystem.model;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;
    private double balanceAfterTransaction;

    public Transaction() {}

    public Transaction(int transactionId, String accountNumber, String transactionType,
                      double amount, LocalDateTime transactionDate, String description,
                      double balanceAfterTransaction) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBalanceAfterTransaction() { return balanceAfterTransaction; }
    public void setBalanceAfterTransaction(double balanceAfterTransaction) { this.balanceAfterTransaction = balanceAfterTransaction; }
} 