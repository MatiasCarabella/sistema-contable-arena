package controller;

import model.Expense;
import model.Income;
import model.Transaction;
import service.TransactionService;
import java.util.List;

/**
 * @deprecated Use {@link TransactionService} directly instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Income createIncome(Income income) {
        return transactionService.createIncome(income);
    }

    public Expense createExpense(Expense expense) {
        return transactionService.createExpense(expense);
    }

    public Transaction findTransaction(int id) {
        return transactionService.findTransaction(id);
    }

    public List<Transaction> listTransactions() {
        return transactionService.listTransactions();
    }

    public void deleteTransaction(int id) {
        transactionService.deleteTransaction(id);
    }
}
