package com.banksystem.gui;

import com.banksystem.model.Transaction;
import com.banksystem.service.BankService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionHistoryFrame extends JFrame {
    private final BankService bankService;
    private final String accountNumber;
    private JTable transactionTable;
    private DefaultTableModel transactionTableModel;

    public TransactionHistoryFrame(String accountNumber, BankService bankService) {
        this.accountNumber = accountNumber;
        this.bankService = bankService;
        initializeUI();
        loadTransactions();
    }

    private void initializeUI() {
        setTitle("Transaction History - Account: " + accountNumber);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Transaction table
        String[] columns = {"Date", "Type", "Amount", "Balance", "Description"};
        transactionTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(transactionTableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> this.dispose());
        buttonPanel.add(closeButton);

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
    }

    private void loadTransactions() {
        // Clear existing rows
        transactionTableModel.setRowCount(0);

        // Get transactions
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
} 