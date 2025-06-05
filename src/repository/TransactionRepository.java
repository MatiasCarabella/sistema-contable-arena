package repository;

import model.Transaction;
import model.Income;
import model.Expense;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private Map<Integer, Transaction> transactions = new HashMap<>();
    private int nextId = 1;

    public Transaction save(Transaction transaction) {
        if (transaction.getId() == 0) {
            transaction.setId(nextId++);
        }
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction findById(int id) {
        return transactions.get(id);
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    public void delete(int id) {
        transactions.remove(id);
    }

    public List<Income> findIncomesByClientId(int clientId) {
        List<Income> result = new ArrayList<>();
        for (Transaction t : transactions.values()) {
            if (t instanceof Income && ((Income)t).getClientId() == clientId) {
                result.add((Income)t);
            }
        }
        return result;
    }

    public List<Expense> findExpensesBySupplierId(int supplierId) {
        List<Expense> result = new ArrayList<>();
        for (Transaction t : transactions.values()) {
            if (t instanceof Expense && ((Expense)t).getSupplierId() == supplierId) {
                result.add((Expense)t);
            }
        }
        return result;
    }
}
