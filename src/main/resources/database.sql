-- Create database
CREATE DATABASE IF NOT EXISTS bank_management;
USE bank_management;

-- Create accounts table
CREATE TABLE IF NOT EXISTS accounts (
    account_number VARCHAR(10) PRIMARY KEY,
    account_type VARCHAR(20) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Create transactions table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(10) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    description TEXT NOT NULL,
    balance_after_transaction DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- Create admins table
CREATE TABLE IF NOT EXISTS admins (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

-- Insert default admin account
INSERT INTO admins (username, password) VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE password = password; 