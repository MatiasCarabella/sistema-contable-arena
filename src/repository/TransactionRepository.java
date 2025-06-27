package repository;

import model.Transaction;
import model.Income;
import model.Expense;
import util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    public Income createIncome(Income income) {
        String sqlTransaction = "INSERT INTO transactions (date, amount, description) VALUES (?, ?, ?)";
        String sqlIncome = "INSERT INTO incomes (transaction_id, client_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtTrans = conn.prepareStatement(sqlTransaction, Statement.RETURN_GENERATED_KEYS);
            stmtTrans.setDate(1, Date.valueOf(income.getDate()));
            stmtTrans.setDouble(2, income.getAmount());
            stmtTrans.setString(3, income.getDescription());
            stmtTrans.executeUpdate();
            ResultSet rs = stmtTrans.getGeneratedKeys();
            if (rs.next()) {
                int transactionId = rs.getInt(1);
                income.setId(transactionId);
                PreparedStatement stmtIncome = conn.prepareStatement(sqlIncome);
                stmtIncome.setInt(1, transactionId);
                stmtIncome.setInt(2, income.getClientId());
                stmtIncome.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return income;
    }

    public Expense createExpense(Expense expense) {
        String sqlTransaction = "INSERT INTO transactions (date, amount, description) VALUES (?, ?, ?)";
        String sqlExpense = "INSERT INTO expenses (transaction_id, supplier_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtTrans = conn.prepareStatement(sqlTransaction, Statement.RETURN_GENERATED_KEYS);
            stmtTrans.setDate(1, Date.valueOf(expense.getDate()));
            stmtTrans.setDouble(2, expense.getAmount());
            stmtTrans.setString(3, expense.getDescription());
            stmtTrans.executeUpdate();
            ResultSet rs = stmtTrans.getGeneratedKeys();
            if (rs.next()) {
                int transactionId = rs.getInt(1);
                expense.setId(transactionId);
                PreparedStatement stmtExpense = conn.prepareStatement(sqlExpense);
                stmtExpense.setInt(1, transactionId);
                stmtExpense.setInt(2, expense.getSupplierId());
                stmtExpense.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expense;
    }

    public Transaction findById(int id) {
        String sql = "SELECT t.*, i.client_id, e.supplier_id FROM transactions t " +
                     "LEFT JOIN incomes i ON t.id = i.transaction_id " +
                     "LEFT JOIN expenses e ON t.id = e.transaction_id WHERE t.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int clientId = rs.getInt("client_id");
                int supplierId = rs.getInt("supplier_id");
                if (clientId > 0) {
                    return new Income(id, date, amount, description, clientId);
                } else if (supplierId > 0) {
                    return new Expense(id, date, amount, description, supplierId);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, i.client_id, e.supplier_id FROM transactions t " +
                     "LEFT JOIN incomes i ON t.id = i.transaction_id " +
                     "LEFT JOIN expenses e ON t.id = e.transaction_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int clientId = rs.getInt("client_id");
                int supplierId = rs.getInt("supplier_id");
                if (clientId > 0) {
                    transactions.add(new Income(id, date, amount, description, clientId));
                } else if (supplierId > 0) {
                    transactions.add(new Expense(id, date, amount, description, supplierId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void delete(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Income> findIncomesByClientId(int clientId) {
        List<Income> result = new ArrayList<>();
        String sql = "SELECT t.*, i.client_id FROM transactions t JOIN incomes i ON t.id = i.transaction_id WHERE i.client_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Income(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Expense> findExpensesBySupplierId(int supplierId) {
        List<Expense> result = new ArrayList<>();
        String sql = "SELECT t.*, e.supplier_id FROM transactions t JOIN expenses e ON t.id = e.transaction_id WHERE e.supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Expense(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getInt("supplier_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
