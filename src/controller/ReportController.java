package controller;

import model.Income;
import model.Expense;
import model.Transaction;
import repository.TransactionRepository;
import java.time.LocalDate;
import java.util.List;

public class ReportController {
    private TransactionRepository transactionRepo;

    public ReportController(TransactionRepository repo) {
        this.transactionRepo = repo;
    }

    public List<Income> getIncomesByClient(int clientId) {
        return transactionRepo.findIncomesByClientId(clientId);
    }

    public List<Expense> getExpensesBySupplier(int supplierId) {
        return transactionRepo.findExpensesBySupplierId(supplierId);
    }

    public List<Transaction> getTransactionsBetween(LocalDate from, LocalDate to) {
        return transactionRepo.findAll().stream()
            .filter(t -> !t.getDate().isBefore(from) && !t.getDate().isAfter(to))
            .toList();
    }
}
