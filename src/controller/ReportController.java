package controller;

import model.Income;
import model.Expense;
import model.Transaction;
import repository.TransactionRepository;
import java.time.LocalDate;
import java.util.List;

public class ReportController {
    private TransactionRepository transactionRepository;

    public ReportController(TransactionRepository repository) {
        this.transactionRepository = repository;
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
            .toList();
    }
}
