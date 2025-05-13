package com.banksystem.gui;

import com.banksystem.model.Account;
import com.banksystem.model.Transaction;
import com.banksystem.service.BankService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
    private final BankService bankService;
    private final String accountNumber;
    private JLabel balanceLabel;
    private JTable transactionTable;
    private DefaultTableModel transactionTableModel;

    public MainFrame(String accountNumber, BankService bankService) {
        this.accountNumber = accountNumber;
        this.bankService = bankService;
        initializeUI();
        updateBalance();
        loadTransactionHistory();
    }

    private void initializeUI() {
        setTitle("Bank Management System - Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel for account info and balance
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Account Number: " + accountNumber));
        balanceLabel = new JLabel();
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(balanceLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(logoutButton);

        // Transaction history panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));

        // Create transaction table
        String[] columns = {"Date", "Type", "Amount", "Balance", "Description"};
        transactionTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(transactionTableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);

        historyPanel.add(scrollPane, BorderLayout.CENTER);

        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        depositButton.addActionListener(e -> handleDeposit());
        withdrawButton.addActionListener(e -> handleWithdraw());
        transferButton.addActionListener(e -> handleTransfer());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void updateBalance() {
        double balance = bankService.getBalance(accountNumber);
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    private void loadTransactionHistory() {
        // Clear existing rows
        transactionTableModel.setRowCount(0);

        // Get transaction history
        List<Transaction> transactions = bankService.getTransactionHistory(accountNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Add transactions to table
        for (Transaction transaction : transactions) {
            transactionTableModel.addRow(new Object[]{
                transaction.getTransactionDate().format(formatter),
                transaction.getTransactionType(),
                String.format("$%.2f", transaction.getAmount()),
                String.format("$%.2f", transaction.getBalanceAfterTransaction()),
                transaction.getDescription()
            });
        }
    }

    private void handleDeposit() {
        String input = JOptionPane.showInputDialog(this,
            "Enter deposit amount:",
            "Deposit",
            JOptionPane.PLAIN_MESSAGE);

        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }

                if (bankService.deposit(accountNumber, amount)) {
                    JOptionPane.showMessageDialog(this,
                        "Deposit successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    updateBalance();
                    loadTransactionHistory();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Deposit failed. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid amount",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleWithdraw() {
        String input = JOptionPane.showInputDialog(this,
            "Enter withdrawal amount:",
            "Withdraw",
            JOptionPane.PLAIN_MESSAGE);

        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }

                if (bankService.withdraw(accountNumber, amount)) {
                    JOptionPane.showMessageDialog(this,
                        "Withdrawal successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    updateBalance();
                    loadTransactionHistory();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Withdrawal failed. Insufficient balance or invalid amount.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid amount",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleTransfer() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField accountField = new JTextField();
        JTextField amountField = new JTextField();
        panel.add(new JLabel("Recipient Account:"));
        panel.add(accountField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Transfer Money", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String recipientAccount = accountField.getText();
            String amountStr = amountField.getText();

            if (recipientAccount.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }

                if (bankService.transfer(accountNumber, recipientAccount, amount)) {
                    JOptionPane.showMessageDialog(this,
                        "Transfer successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    updateBalance();
                    loadTransactionHistory();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Transfer failed. Please check the account number and your balance.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid amount",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 