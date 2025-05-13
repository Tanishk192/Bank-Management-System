package com.banksystem.model;

import java.time.LocalDateTime;

public class Account {
    private String accountNumber;
    private String accountType;
    private String customerName;
    private String email;
    private String phone;
    private String address;
    private double balance;
    private LocalDateTime createdAt;
    private String password;

    public Account() {}

    public Account(String accountNumber, String accountType, String customerName, 
                  String email, String phone, String address, double balance, 
                  LocalDateTime createdAt, String password) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.createdAt = createdAt;
        this.password = password;
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 