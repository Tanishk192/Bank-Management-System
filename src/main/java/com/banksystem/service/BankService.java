package com.banksystem.service;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.TransactionDAO;
import com.banksystem.model.Account;
import com.banksystem.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class BankService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public BankService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public String createAccount(String accountType, String customerName, String email,
                              String phone, String address, double initialDeposit, String password) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(
            accountNumber,
            accountType,
            customerName,
            email,
            phone,
            address,
            initialDeposit,
            LocalDateTime.now(),
            password
        );

        if (accountDAO.createAccount(account)) {
            // Record initial deposit transaction
            Transaction transaction = new Transaction(
                0,
                accountNumber,
                "DEPOSIT",
                initialDeposit,
                LocalDateTime.now(),
                "Initial deposit",
                initialDeposit
            );
            transactionDAO.addTransaction(transaction);
            return accountNumber;
        }
        return null;
    }

    public boolean deposit(String accountNumber, double amount) {
        Account account = accountDAO.getAccount(accountNumber);
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            if (accountDAO.updateBalance(accountNumber, newBalance)) {
                Transaction transaction = new Transaction(
                    0,
                    accountNumber,
                    "DEPOSIT",
                    amount,
                    LocalDateTime.now(),
                    "Deposit transaction",
                    newBalance
                );
                return transactionDAO.addTransaction(transaction);
            }
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accountDAO.getAccount(accountNumber);
        if (account != null && account.getBalance() >= amount) {
            double newBalance = account.getBalance() - amount;
            if (accountDAO.updateBalance(accountNumber, newBalance)) {
                Transaction transaction = new Transaction(
                    0,
                    accountNumber,
                    "WITHDRAWAL",
                    amount,
                    LocalDateTime.now(),
                    "Withdrawal transaction",
                    newBalance
                );
                return transactionDAO.addTransaction(transaction);
            }
        }
        return false;
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        Account sender = accountDAO.getAccount(fromAccount);
        Account receiver = accountDAO.getAccount(toAccount);

        if (sender != null && receiver != null && sender.getBalance() >= amount) {
            // Withdraw from sender
            if (withdraw(fromAccount, amount)) {
                // Deposit to receiver
                if (deposit(toAccount, amount)) {
                    return true;
                } else {
                    // Rollback withdrawal if deposit fails
                    deposit(fromAccount, amount);
                }
            }
        }
        return false;
    }

    public double getBalance(String accountNumber) {
        Account account = accountDAO.getAccount(accountNumber);
        return account != null ? account.getBalance() : -1;
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionDAO.getTransactionHistory(accountNumber);
    }

    public boolean validateLogin(String accountNumber, String password) {
        return accountDAO.validateLogin(accountNumber, password);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
} 