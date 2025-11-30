package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Expense;
import service.ReportService;
import service.SupplierService;
import service.TransactionService;

public class ExpensesMenu {
  private final ReportService reportService;
  private final TransactionService transactionService;
  private final SupplierService supplierService;
  private final Scanner scanner;

  public ExpensesMenu(
      ReportService reportService,
      TransactionService transactionService,
      SupplierService supplierService,
      Scanner scanner) {
    this.reportService = reportService;
    this.transactionService = transactionService;
    this.supplierService = supplierService;
    this.scanner = scanner;
  }

  public void show() {
    int option;
    do {
      System.out.println("\n" + ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.title("|  ")
              + ConsoleColors.BOLD_WHITE
              + "EXPENSE MANAGEMENT"
              + ConsoleColors.title("      |"));
      System.out.println(ConsoleColors.title("+-----------------------------+"));
      System.out.println(ConsoleColors.BOLD_RED + "1." + ConsoleColors.RESET + " Register expense");
      System.out.println(ConsoleColors.BLUE + "2." + ConsoleColors.RESET + " Delete expense");
      System.out.println(
          ConsoleColors.BLUE + "3." + ConsoleColors.RESET + " List expenses by supplier");
      System.out.println(ConsoleColors.RED + "0." + ConsoleColors.RESET + " Back");
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "\nSelect an option: " + ConsoleColors.RESET);
      try {
        switch (option) {
          case 1 -> registerExpense();
          case 2 -> deleteExpense();
          case 3 -> listExpensesBySupplier();
          case 0 -> {}
          default -> System.out.println(ConsoleColors.warning("[!] Invalid option"));
        }
      } catch (ValidationException | EntityNotFoundException e) {
        System.out.println(ConsoleColors.error("[ERROR] " + e.getMessage()));
      }
    } while (option != 0);
  }

  private void registerExpense() {
    System.out.println("\n" + ConsoleColors.info("=== Register Expense ==="));
    int supplierId = InputUtils.readInt(scanner, "Supplier ID: ");
    double amount = InputUtils.readDouble(scanner, "Amount: $");
    String description = InputUtils.readString(scanner, "Description: ");
    LocalDate date = InputUtils.readDate(scanner, "Date (YYYY-MM-DD): ");

    Expense expense = new Expense(0, date, amount, description, supplierId);
    transactionService.createExpense(expense);
    System.out.println(
        ConsoleColors.success("\n[OK] Expense registered with ID: " + expense.getId()));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void deleteExpense() {
    System.out.println("\n" + ConsoleColors.info("=== Delete Expense ==="));
    int id = InputUtils.readInt(scanner, "Expense ID: ");
    transactionService.deleteTransaction(id);
    System.out.println(ConsoleColors.success("\n[OK] Expense deleted successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void listExpensesBySupplier() {
    System.out.println("\n" + ConsoleColors.info("=== Expenses by Supplier ==="));
    int supplierId = InputUtils.readInt(scanner, "Supplier ID: ");
    List<Expense> expenses = reportService.getExpensesBySupplier(supplierId);

    if (expenses.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No expenses found for this supplier"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_RED + "\nTotal records: " + expenses.size() + ConsoleColors.RESET);
      for (Expense expense : expenses) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " "
                + ConsoleColors.BOLD_RED
                + "$%.2f"
                + ConsoleColors.RESET
                + " | %s | %s%n",
            expense.getId(),
            expense.getAmount(),
            expense.getDescription(),
            expense.getDate());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }
}
