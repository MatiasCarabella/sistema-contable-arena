package controller;

import exception.ValidationException;
import java.util.List;
import model.Expense;
import model.Income;
import model.Transaction;
import service.TransactionService;

/**
 * Controller for transaction operations. Handles input validation before delegating to service
 * layer.
 */
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  public Income createIncome(Income income) {
    validateIncome(income);
    return transactionService.createIncome(income);
  }

  public Expense createExpense(Expense expense) {
    validateExpense(expense);
    return transactionService.createExpense(expense);
  }

  public Transaction findTransaction(int id) {
    validateId(id);
    return transactionService.findTransaction(id);
  }

  public List<Transaction> listTransactions() {
    return transactionService.listTransactions();
  }

  public void deleteTransaction(int id) {
    validateId(id);
    transactionService.deleteTransaction(id);
  }

  private void validateId(int id) {
    if (id <= 0) {
      throw new ValidationException("ID must be positive");
    }
  }

  private void validateIncome(Income income) {
    if (income == null) {
      throw new ValidationException("Income cannot be null");
    }
    if (income.getAmount() <= 0) {
      throw new ValidationException("Income amount must be positive");
    }
    final String description = income.getDescription();
    if (description == null || description.trim().isEmpty()) {
      throw new ValidationException("Income description cannot be empty");
    }
    if (income.getDate() == null) {
      throw new ValidationException("Income date cannot be null");
    }
    if (income.getClientId() <= 0) {
      throw new ValidationException("Client ID must be positive");
    }
  }

  private void validateExpense(Expense expense) {
    if (expense == null) {
      throw new ValidationException("Expense cannot be null");
    }
    if (expense.getAmount() <= 0) {
      throw new ValidationException("Expense amount must be positive");
    }
    final String description = expense.getDescription();
    if (description == null || description.trim().isEmpty()) {
      throw new ValidationException("Expense description cannot be empty");
    }
    if (expense.getDate() == null) {
      throw new ValidationException("Expense date cannot be null");
    }
    if (expense.getSupplierId() <= 0) {
      throw new ValidationException("Supplier ID must be positive");
    }
  }
}
