package service;

import exception.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;
import model.Expense;
import model.Income;
import model.Transaction;
import repository.ClientRepository;
import repository.SupplierRepository;
import repository.TransactionRepository;

public class TransactionService {
  private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
  private final TransactionRepository transactionRepository;
  private final ClientRepository clientRepository;
  private final SupplierRepository supplierRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      ClientRepository clientRepository,
      SupplierRepository supplierRepository) {
    this.transactionRepository = transactionRepository;
    this.clientRepository = clientRepository;
    this.supplierRepository = supplierRepository;
  }

  public Income createIncome(Income income) {
    // Verify client exists
    clientRepository
        .findById(income.getClientId())
        .orElseThrow(
            () -> new EntityNotFoundException("Client not found with ID: " + income.getClientId()));

    return transactionRepository.createIncome(income);
  }

  public Expense createExpense(Expense expense) {
    // Verify supplier exists
    supplierRepository
        .findById(expense.getSupplierId())
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Supplier not found with ID: " + expense.getSupplierId()));

    return transactionRepository.createExpense(expense);
  }

  public Transaction findTransaction(int id) {
    return transactionRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
  }

  public List<Transaction> listTransactions() {
    return transactionRepository.findAll();
  }

  public void deleteTransaction(int id) {
    transactionRepository.deleteById(id);
    LOGGER.info("Transaction deleted with ID: " + id);
  }
}
