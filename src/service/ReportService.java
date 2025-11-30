package service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import model.Expense;
import model.Income;
import model.Transaction;
import repository.TransactionRepository;

public class ReportService {
  private final TransactionRepository transactionRepository;

  public ReportService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Income> getIncomesByClient(int clientId) {
    return transactionRepository.findIncomesByClientId(clientId);
  }

  public List<Expense> getExpensesBySupplier(int supplierId) {
    return transactionRepository.findExpensesBySupplierId(supplierId);
  }

  public List<Transaction> getTransactionsBetween(LocalDate from, LocalDate to) {
    return transactionRepository.findAll().stream()
        .filter(t -> !t.getDate().isBefore(from) && !t.getDate().isAfter(to))
        .collect(Collectors.toList());
  }

  public double calculateTotalIncome(LocalDate from, LocalDate to) {
    return getTransactionsBetween(from, to).stream()
        .filter(t -> t instanceof Income)
        .mapToDouble(Transaction::getAmount)
        .sum();
  }

  public double calculateTotalExpense(LocalDate from, LocalDate to) {
    return getTransactionsBetween(from, to).stream()
        .filter(t -> t instanceof Expense)
        .mapToDouble(Transaction::getAmount)
        .sum();
  }

  public double calculateBalance(LocalDate from, LocalDate to) {
    return calculateTotalIncome(from, to) - calculateTotalExpense(from, to);
  }
}
