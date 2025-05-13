package com.banksystem.dao;

import com.banksystem.model.Transaction;
import com.banksystem.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, transaction_date, description, balance_after_transaction) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getAccountNumber());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setString(5, transaction.getDescription());
            stmt.setDouble(6, transaction.getBalanceAfterTransaction());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(extractTransactionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("transaction_id"),
            rs.getString("account_number"),
            rs.getString("transaction_type"),
            rs.getDouble("amount"),
            rs.getTimestamp("transaction_date").toLocalDateTime(),
            rs.getString("description"),
            rs.getDouble("balance_after_transaction")
        );
    }
} 