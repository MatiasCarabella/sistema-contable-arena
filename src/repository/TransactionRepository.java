package repository;

import config.DatabaseConfig;
import exception.DatabaseException;
import model.Transaction;
import model.Income;
import model.Expense;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransactionRepository implements Repository<Transaction, Integer> {
    private static final Logger LOGGER = Logger.getLogger(TransactionRepository.class.getName());
    @Override
    public Transaction save(Transaction transaction) {
        if (transaction instanceof Income) {
            return createIncome((Income) transaction);
        } else if (transaction instanceof Expense) {
            return createExpense((Expense) transaction);
        }
        throw new IllegalArgumentException("Unknown transaction type");
    }

    public Income createIncome(Income income) {
        String sqlTransaction = "INSERT INTO transactions (date, amount, description) VALUES (?, ?, ?)";
        String sqlIncome = "INSERT INTO incomes (transaction_id, client_id) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmtTrans = conn.prepareStatement(sqlTransaction, Statement.RETURN_GENERATED_KEYS)) {
                stmtTrans.setDate(1, Date.valueOf(income.getDate()));
                stmtTrans.setDouble(2, income.getAmount());
                stmtTrans.setString(3, income.getDescription());
                stmtTrans.executeUpdate();
                
                try (ResultSet rs = stmtTrans.getGeneratedKeys()) {
                    if (rs.next()) {
                        int transactionId = rs.getInt(1);
                        income.setId(transactionId);
                        
                        try (PreparedStatement stmtIncome = conn.prepareStatement(sqlIncome)) {
                            stmtIncome.setInt(1, transactionId);
                            stmtIncome.setInt(2, income.getClientId());
                            stmtIncome.executeUpdate();
                        }
                    }
                }
            }
            conn.commit();
            LOGGER.info("Income created with ID: " + income.getId());
            return income;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    LOGGER.severe("Error rolling back transaction: " + ex.getMessage());
                }
            }
            LOGGER.severe("Error creating income: " + e.getMessage());
            throw new DatabaseException("Failed to create income", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.warning("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public Expense createExpense(Expense expense) {
        String sqlTransaction = "INSERT INTO transactions (date, amount, description) VALUES (?, ?, ?)";
        String sqlExpense = "INSERT INTO expenses (transaction_id, supplier_id) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmtTrans = conn.prepareStatement(sqlTransaction, Statement.RETURN_GENERATED_KEYS)) {
                stmtTrans.setDate(1, Date.valueOf(expense.getDate()));
                stmtTrans.setDouble(2, expense.getAmount());
                stmtTrans.setString(3, expense.getDescription());
                stmtTrans.executeUpdate();
                
                try (ResultSet rs = stmtTrans.getGeneratedKeys()) {
                    if (rs.next()) {
                        int transactionId = rs.getInt(1);
                        expense.setId(transactionId);
                        
                        try (PreparedStatement stmtExpense = conn.prepareStatement(sqlExpense)) {
                            stmtExpense.setInt(1, transactionId);
                            stmtExpense.setInt(2, expense.getSupplierId());
                            stmtExpense.executeUpdate();
                        }
                    }
                }
            }
            conn.commit();
            LOGGER.info("Expense created with ID: " + expense.getId());
            return expense;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    LOGGER.severe("Error rolling back transaction: " + ex.getMessage());
                }
            }
            LOGGER.severe("Error creating expense: " + e.getMessage());
            throw new DatabaseException("Failed to create expense", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.warning("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public Optional<Transaction> findById(Integer id) {
        String sql = "SELECT t.*, i.client_id, e.supplier_id FROM transactions t " +
                     "LEFT JOIN incomes i ON t.id = i.transaction_id " +
                     "LEFT JOIN expenses e ON t.id = e.transaction_id WHERE t.id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding transaction by ID: " + e.getMessage());
            throw new DatabaseException("Failed to find transaction", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, i.client_id, e.supplier_id FROM transactions t " +
                     "LEFT JOIN incomes i ON t.id = i.transaction_id " +
                     "LEFT JOIN expenses e ON t.id = e.transaction_id " +
                     "ORDER BY t.date DESC, t.id DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = mapResultSetToTransaction(rs);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding all transactions: " + e.getMessage());
            throw new DatabaseException("Failed to retrieve transactions", e);
        }
        return transactions;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Transaction deleted with ID: " + id + " (rows affected: " + rowsAffected + ")");
        } catch (SQLException e) {
            LOGGER.severe("Error deleting transaction: " + e.getMessage());
            throw new DatabaseException("Failed to delete transaction", e);
        }
    }

    public List<Income> findIncomesByClientId(int clientId) {
        List<Income> result = new ArrayList<>();
        String sql = "SELECT t.*, i.client_id FROM transactions t " +
                     "JOIN incomes i ON t.id = i.transaction_id " +
                     "WHERE i.client_id = ? ORDER BY t.date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Income(
                        rs.getInt("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getInt("client_id")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding incomes by client ID: " + e.getMessage());
            throw new DatabaseException("Failed to retrieve incomes", e);
        }
        return result;
    }

    public List<Expense> findExpensesBySupplierId(int supplierId) {
        List<Expense> result = new ArrayList<>();
        String sql = "SELECT t.*, e.supplier_id FROM transactions t " +
                     "JOIN expenses e ON t.id = e.transaction_id " +
                     "WHERE e.supplier_id = ? ORDER BY t.date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Expense(
                        rs.getInt("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getInt("supplier_id")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding expenses by supplier ID: " + e.getMessage());
            throw new DatabaseException("Failed to retrieve expenses", e);
        }
        return result;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        LocalDate date = rs.getDate("date").toLocalDate();
        double amount = rs.getDouble("amount");
        String description = rs.getString("description");
        int clientId = rs.getInt("client_id");
        int supplierId = rs.getInt("supplier_id");
        
        if (clientId > 0) {
            return new Income(id, date, amount, description, clientId);
        } else if (supplierId > 0) {
            return new Expense(id, date, amount, description, supplierId);
        }
        return null;
    }
}
