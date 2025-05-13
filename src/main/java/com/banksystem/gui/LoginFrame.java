package com.banksystem.gui;

import com.banksystem.service.BankService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final BankService bankService;
    private JTextField accountNumberField;
    private JPasswordField passwordField;

    public LoginFrame() {
        this.bankService = new BankService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bank Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Bank Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel accountLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginPanel.add(accountLabel);
        loginPanel.add(accountNumberField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");
        JButton adminLoginButton = new JButton("Admin Login");

        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(adminLoginButton);

        // Add components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginPanel);
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        createAccountButton.addActionListener(e -> openCreateAccountFrame());
        adminLoginButton.addActionListener(e -> openAdminLoginFrame());
    }

    private void handleLogin() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());

        if (accountNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both account number and password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (bankService.validateLogin(accountNumber, password)) {
            // Open main banking window
            SwingUtilities.invokeLater(() -> {
                new MainFrame(accountNumber, bankService).setVisible(true);
                this.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid account number or password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openCreateAccountFrame() {
        SwingUtilities.invokeLater(() -> {
            new CreateAccountFrame(bankService).setVisible(true);
            this.dispose();
        });
    }

    private void openAdminLoginFrame() {
        SwingUtilities.invokeLater(() -> {
            new AdminLoginFrame().setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
} 