package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import model.Expense;
import service.ReportService;
import service.SupplierService;
import service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpensesMenu {
    private final ReportService reportService;
    private final TransactionService transactionService;
    private final SupplierService supplierService;
    private final Scanner scanner;

    public ExpensesMenu(ReportService reportService, TransactionService transactionService, 
                       SupplierService supplierService, Scanner scanner) {
        this.reportService = reportService;
        this.transactionService = transactionService;
        this.supplierService = supplierService;
        this.scanner = scanner;
    }

    public void show() {
        int option;
        do {
            System.out.println("\n--- Expense Management ---");
            System.out.println("1. Register expense associated with supplier");
            System.out.println("2. Delete expense");
            System.out.println("3. List expenses by supplier");
            System.out.println("0. Back");
            option = InputUtils.readInt(scanner, "Select an option: ");
            try {
                switch (option) {
                    case 1 -> registerExpense();
                    case 2 -> deleteExpense();
                    case 3 -> listExpensesBySupplier();
                    case 0 -> {}
                    default -> System.out.println("Invalid option");
                }
            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void registerExpense() {
        System.out.println("--- Register Expense Associated with Supplier ---");
        int supplierId = InputUtils.readInt(scanner, "Supplier ID: ");
        double amount = InputUtils.readDouble(scanner, "Amount: ");
        String description = InputUtils.readString(scanner, "Description: ");
        LocalDate date = InputUtils.readDate(scanner, "Date (YYYY-MM-DD): ");
        
        Expense expense = new Expense(0, date, amount, description, supplierId);
        transactionService.createExpense(expense);
        System.out.println("Expense registered successfully with ID: " + expense.getId());
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void deleteExpense() {
        System.out.println("--- Delete Expense ---");
        int id = InputUtils.readInt(scanner, "Expense ID: ");
        transactionService.deleteTransaction(id);
        System.out.println("Expense deleted successfully");
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void listExpensesBySupplier() {
        System.out.println("--- Expenses by Supplier ---");
        int supplierId = InputUtils.readInt(scanner, "Supplier ID: ");
        List<Expense> expenses = reportService.getExpensesBySupplier(supplierId);
        
        if (expenses.isEmpty()) {
            System.out.println("No expenses found for this supplier");
        } else {
            System.out.println("\nExpense records:");
            for (Expense expense : expenses) {
                System.out.printf("ID: %d | Amount: $%.2f | %s | Date: %s%n",
                    expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate());
            }
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }
}
