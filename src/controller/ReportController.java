package controller;

import exception.ValidationException;
import java.time.LocalDate;
import java.util.List;
import model.Expense;
import model.Income;
import model.Transaction;
import service.ReportService;

/**
 * Controller for report operations. Handles input validation before delegating to service layer.
 */
public class ReportController {
  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  public List<Income> getIncomesByClient(int clientId) {
    validateId(clientId, "Client");
    return reportService.getIncomesByClient(clientId);
  }

  public List<Expense> getExpensesBySupplier(int supplierId) {
    validateId(supplierId, "Supplier");
    return reportService.getExpensesBySupplier(supplierId);
  }

  public List<Transaction> getTransactionsBetween(LocalDate from, LocalDate to) {
    validateDateRange(from, to);
    return reportService.getTransactionsBetween(from, to);
  }

  public double calculateTotalIncome(LocalDate from, LocalDate to) {
    validateDateRange(from, to);
    return reportService.calculateTotalIncome(from, to);
  }

  public double calculateTotalExpense(LocalDate from, LocalDate to) {
    validateDateRange(from, to);
    return reportService.calculateTotalExpense(from, to);
  }

  public double calculateBalance(LocalDate from, LocalDate to) {
    validateDateRange(from, to);
    return reportService.calculateBalance(from, to);
  }

  private void validateId(int id, String entityType) {
    if (id <= 0) {
      throw new ValidationException(entityType + " ID must be positive");
    }
  }

  private void validateDateRange(LocalDate from, LocalDate to) {
    if (from == null) {
      throw new ValidationException("Start date cannot be null");
    }
    if (to == null) {
      throw new ValidationException("End date cannot be null");
    }
    if (from.isAfter(to)) {
      throw new ValidationException("Start date must be before or equal to end date");
    }
  }
}
