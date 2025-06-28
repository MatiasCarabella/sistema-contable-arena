-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS sistema_contable_arena;
USE sistema_contable_arena;

-- Creación de tablas
CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cuit VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(150),
    phone VARCHAR(20)
);

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cuit VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(150),
    phone VARCHAR(20)
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    description TEXT
);

CREATE TABLE incomes (
    transaction_id INT PRIMARY KEY,
    client_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE expenses (
    transaction_id INT PRIMARY KEY,
    supplier_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
);

-- Inserción de datos
INSERT INTO clients (name, cuit, address, phone)
VALUES ('Taller El Rayo', '20-12345678-9', 'Av. Córdoba 1234', '011-4567-8901');

INSERT INTO suppliers (name, cuit, address, phone)
VALUES ('Distribuidora Autopartes SA', '30-87654321-0', 'Ruta 8 km 45', '011-2345-6789');

INSERT INTO transactions (date, amount, description)
VALUES ('2025-05-18', 158000.00, 'Venta de repuestos - Factura 0001-000123');

INSERT INTO incomes (transaction_id, client_id)
VALUES (1, 1);

INSERT INTO transactions (date, amount, description)
VALUES ('2025-05-19', 95000.00, 'Compra de piezas - Factura 0002-000456');

INSERT INTO expenses (transaction_id, supplier_id)
VALUES (2, 1);

-- Consultas
SELECT * FROM clients;

SELECT t.id, t.date, t.amount, t.description, c.name AS client_name, c.cuit
FROM transactions t
JOIN incomes i ON t.id = i.transaction_id
JOIN clients c ON i.client_id = c.id;

SELECT t.id, t.date, t.amount, t.description, s.name AS supplier_name, s.cuit
FROM transactions t
JOIN expenses e ON t.id = e.transaction_id
JOIN suppliers s ON e.supplier_id = s.id;

SELECT
    (SELECT SUM(amount) FROM transactions t
     JOIN incomes i ON t.id = i.transaction_id
     WHERE t.date BETWEEN '2025-05-01' AND '2025-05-31') AS total_ingresos,
    (SELECT SUM(amount) FROM transactions t
     JOIN expenses e ON t.id = e.transaction_id
     WHERE t.date BETWEEN '2025-05-01' AND '2025-05-31') AS total_gastos;
