package com.banksystem.dao;

import com.banksystem.model.Admin;
import com.banksystem.util.DatabaseConfig;

import java.sql.*;

public class AdminDAO {
    private Connection connection;

    public AdminDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    public boolean validateAdmin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAdmin(Admin admin) {
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 