package view;

import model.Transaction;
import service.ReportService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReportsMenu {
    private final ReportService reportService;
    private final Scanner scanner;

    public ReportsMenu(ReportService reportService, Scanner scanner) {
        this.reportService = reportService;
        this.scanner = scanner;
    }

    public void show() {
        int option;
        do {
            System.out.println("\n--- Reports ---");
            System.out.println("1. View transaction history (income and expenses)");
            System.out.println("2. Generate balance report by date range");
            System.out.println("0. Back");
            option = InputUtils.readInt(scanner, "Select an option: ");
            switch (option) {
                case 1 -> listAllTransactions();
                case 2 -> generateBalanceReport();
                case 0 -> {}
                default -> System.out.println("Invalid option");
            }
        } while (option != 0);
    }

    private void listAllTransactions() {
        System.out.println("--- Transaction History (Income and Expenses) ---");
        List<Transaction> transactions = reportService.getTransactionsBetween(LocalDate.MIN, LocalDate.MAX);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            for (Transaction t : transactions) {
                String type = t.getClass().getSimpleName();
                System.out.printf("ID: %d | [%s] | Amount: $%.2f | %s | Date: %s%n",
                    t.getId(), type, t.getAmount(), t.getDescription(), t.getDate());
            }
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void generateBalanceReport() {
        System.out.println("--- Balance Report by Date Range ---");
        LocalDate from = InputUtils.readDate(scanner, "From (YYYY-MM-DD): ");
        LocalDate to = InputUtils.readDate(scanner, "To (YYYY-MM-DD): ");
        
        double totalIncome = reportService.calculateTotalIncome(from, to);
        double totalExpense = reportService.calculateTotalExpense(from, to);
        double balance = reportService.calculateBalance(from, to);
        
        System.out.println("\n=== Balance Report ===");
        System.out.printf("Period: %s to %s%n", from, to);
        System.out.printf("Total Income:  $%.2f%n", totalIncome);
        System.out.printf("Total Expense: $%.2f%n", totalExpense);
        System.out.println("----------------------");
        System.out.printf("Balance:       $%.2f%n", balance);
        
        if (balance > 0) {
            System.out.println("Status: Positive balance");
        } else if (balance < 0) {
            System.out.println("Status: Negative balance");
        } else {
            System.out.println("Status: Balanced");
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }
}
