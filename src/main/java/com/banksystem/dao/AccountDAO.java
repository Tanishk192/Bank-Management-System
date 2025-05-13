package com.banksystem.dao;

import com.banksystem.model.Account;
import com.banksystem.util.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection connection;

    public AccountDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (account_number, account_type, customer_name, email, phone, address, balance, created_at, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getAccountType());
            stmt.setString(3, account.getCustomerName());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getPhone());
            stmt.setString(6, account.getAddress());
            stmt.setDouble(7, account.getBalance());
            stmt.setTimestamp(8, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setString(9, account.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccount(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(extractAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean validateLogin(String accountNumber, String password) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        return new Account(
            rs.getString("account_number"),
            rs.getString("account_type"),
            rs.getString("customer_name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getDouble("balance"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getString("password")
        );
    }
} 