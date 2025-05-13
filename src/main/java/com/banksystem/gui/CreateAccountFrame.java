package com.banksystem.gui;

import com.banksystem.service.BankService;

import javax.swing.*;
import java.awt.*;

public class CreateAccountFrame extends JFrame {
    private final BankService bankService;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JTextField initialDepositField;
    private JPasswordField passwordField;
    private JComboBox<String> accountTypeCombo;

    public CreateAccountFrame(BankService bankService) {
        this.bankService = bankService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Create New Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Account Type
        formPanel.add(new JLabel("Account Type:"));
        accountTypeCombo = new JComboBox<>(new String[]{"Savings", "Current"});
        formPanel.add(accountTypeCombo);

        // Name
        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        // Email
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // Phone
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        // Address
        formPanel.add(new JLabel("Address:"));
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);
        formPanel.add(addressScroll);

        // Initial Deposit
        formPanel.add(new JLabel("Initial Deposit:"));
        initialDepositField = new JTextField();
        formPanel.add(initialDepositField);

        // Password
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton createButton = new JButton("Create Account");
        JButton backButton = new JButton("Back to Login");

        buttonPanel.add(createButton);
        buttonPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        createButton.addActionListener(e -> handleCreateAccount());
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void handleCreateAccount() {
        // Validate input fields
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
            phoneField.getText().isEmpty() || addressArea.getText().isEmpty() ||
            initialDepositField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Creation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double initialDeposit = Double.parseDouble(initialDepositField.getText());
            if (initialDeposit <= 0) {
                throw new NumberFormatException();
            }

            String accountNumber = bankService.createAccount(
                accountTypeCombo.getSelectedItem().toString(),
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressArea.getText(),
                initialDeposit,
                new String(passwordField.getPassword())
            );

            if (accountNumber != null) {
                JOptionPane.showMessageDialog(this,
                    "Account created successfully!\nYour account number is: " + accountNumber,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Return to login screen
                new LoginFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to create account. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid initial deposit amount",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 