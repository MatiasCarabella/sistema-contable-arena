package controller;

import model.Income;
import model.Expense;
import model.Transaction;
import repository.TransactionRepository;
import java.util.List;

public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository repository) {
        this.transactionRepository = repository;
    }

    public Income createIncome(Income income) {
        return transactionRepository.createIncome(income);
    }

    public Expense createExpense(Expense expense) {
        return transactionRepository.createExpense(expense);
    }

    public Transaction findTransaction(int id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> listTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(int id) {
        transactionRepository.delete(id);
    }
}
