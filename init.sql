-- Database initialization script
CREATE DATABASE IF NOT EXISTS accounting_system;
USE accounting_system;

-- Create tables
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cuit VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(150),
    phone VARCHAR(20),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cuit VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(150),
    phone VARCHAR(20),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    description TEXT,
    INDEX idx_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS incomes (
    transaction_id INT PRIMARY KEY,
    client_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    INDEX idx_client (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS expenses (
    transaction_id INT PRIMARY KEY,
    supplier_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE,
    INDEX idx_supplier (supplier_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample data
INSERT INTO clients (name, cuit, address, phone)
VALUES 
    ('Lightning Auto Shop', '20-12345678-9', '1234 Cordoba Ave', '011-4567-8901'),
    ('Tech Solutions Inc', '20-98765432-1', '5678 Buenos Aires St', '011-9876-5432');

INSERT INTO suppliers (name, cuit, address, phone)
VALUES 
    ('Auto Parts Distributor SA', '30-87654321-0', 'Route 8 km 45', '011-2345-6789'),
    ('Office Supplies Co', '30-11223344-5', '9012 Rivadavia Ave', '011-5555-1234');

INSERT INTO transactions (date, amount, description)
VALUES 
    ('2025-05-18', 158000.00, 'Parts sale - Invoice 0001-000123'),
    ('2025-05-19', 95000.00, 'Parts purchase - Invoice 0002-000456'),
    ('2025-05-20', 45000.00, 'Repair service - Invoice 0001-000124'),
    ('2025-05-21', 12000.00, 'Office supplies - Invoice 0003-000789');

INSERT INTO incomes (transaction_id, client_id)
VALUES 
    (1, 1),
    (3, 2);

INSERT INTO expenses (transaction_id, supplier_id)
VALUES 
    (2, 1),
    (4, 2);
