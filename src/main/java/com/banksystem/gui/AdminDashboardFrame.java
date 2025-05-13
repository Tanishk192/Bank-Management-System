package com.banksystem.gui;

import com.banksystem.model.Account;
import com.banksystem.service.BankService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    private final BankService bankService;
    private JTable accountsTable;
    private DefaultTableModel accountsTableModel;

    public AdminDashboardFrame() {
        this.bankService = new BankService();
        initializeUI();
        loadAccounts();
    }

    private void initializeUI() {
        setTitle("Bank Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Accounts table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("All Accounts"));

        // Create accounts table
        String[] columns = {"Account Number", "Type", "Customer Name", "Email", "Phone", "Balance", "Created At"};
        accountsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountsTable = new JTable(accountsTableModel);
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Refresh");
        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(refreshButton);
        buttonPanel.add(viewTransactionsButton);
        buttonPanel.add(logoutButton);

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        refreshButton.addActionListener(e -> loadAccounts());
        viewTransactionsButton.addActionListener(e -> viewSelectedAccountTransactions());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void loadAccounts() {
        // Clear existing rows
        accountsTableModel.setRowCount(0);

        // Get all accounts
        List<Account> accounts = bankService.getAllAccounts();

        // Add accounts to table
        for (Account account : accounts) {
            accountsTableModel.addRow(new Object[]{
                account.getAccountNumber(),
                account.getAccountType(),
                account.getCustomerName(),
                account.getEmail(),
                account.getPhone(),
                String.format("$%.2f", account.getBalance()),
                account.getCreatedAt().toString()
            });
        }
    }

    private void viewSelectedAccountTransactions() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an account to view transactions",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String accountNumber = accountsTableModel.getValueAt(selectedRow, 0).toString();
        SwingUtilities.invokeLater(() -> {
            new TransactionHistoryFrame(accountNumber, bankService).setVisible(true);
        });
    }
} 