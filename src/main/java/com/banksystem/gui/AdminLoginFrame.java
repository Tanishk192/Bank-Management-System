package com.banksystem.gui;

import com.banksystem.dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private final AdminDAO adminDAO;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginFrame() {
        this.adminDAO = new AdminDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bank Management System - Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginPanel);
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminDAO.validateAdmin(username, password)) {
            SwingUtilities.invokeLater(() -> {
                new AdminDashboardFrame().setVisible(true);
                this.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 