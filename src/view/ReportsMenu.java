package view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Transaction;
import service.ReportService;

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
      System.out.println("\n" + ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.title("|       ")
              + ConsoleColors.BOLD_WHITE
              + "REPORTS"
              + ConsoleColors.title("            |"));
      System.out.println(ConsoleColors.title("+-----------------------------+"));
      System.out.println(ConsoleColors.BLUE + "1." + ConsoleColors.RESET + " Transaction history");
      System.out.println(ConsoleColors.BLUE + "2." + ConsoleColors.RESET + " Balance report");
      System.out.println(ConsoleColors.RED + "0." + ConsoleColors.RESET + " Back");
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "\nSelect an option: " + ConsoleColors.RESET);
      switch (option) {
        case 1 -> listAllTransactions();
        case 2 -> generateBalanceReport();
        case 0 -> {}
        default -> System.out.println(ConsoleColors.warning("[!] Invalid option"));
      }
    } while (option != 0);
  }

  private void listAllTransactions() {
    System.out.println("\n" + ConsoleColors.info("=== Transaction History ==="));
    List<Transaction> transactions =
        reportService.getTransactionsBetween(LocalDate.MIN, LocalDate.MAX);

    if (transactions.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No transactions found"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_CYAN
              + "\nTotal transactions: "
              + transactions.size()
              + ConsoleColors.RESET);
      for (Transaction t : transactions) {
        String type = t.getClass().getSimpleName();
        String typeColor =
            type.equals("Income") ? ConsoleColors.BOLD_GREEN : ConsoleColors.BOLD_RED;
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " [%s%s%s] | $%.2f | %s | %s%n",
            t.getId(),
            typeColor,
            type,
            ConsoleColors.RESET,
            t.getAmount(),
            t.getDescription(),
            t.getDate());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void generateBalanceReport() {
    System.out.println("\n" + ConsoleColors.info("=== Balance Report ==="));
    LocalDate from = InputUtils.readDate(scanner, "From (YYYY-MM-DD): ");
    LocalDate to = InputUtils.readDate(scanner, "To (YYYY-MM-DD): ");

    double totalIncome = reportService.calculateTotalIncome(from, to);
    double totalExpense = reportService.calculateTotalExpense(from, to);
    double balance = reportService.calculateBalance(from, to);

    System.out.println("\n" + ConsoleColors.BOLD_CYAN + "===============================");
    System.out.println("      FINANCIAL SUMMARY");
    System.out.println("===============================" + ConsoleColors.RESET);
    System.out.printf("Period: %s%s to %s%s%n", ConsoleColors.BOLD, from, to, ConsoleColors.RESET);
    System.out.printf(
        ConsoleColors.BOLD_GREEN + "Total Income:  " + ConsoleColors.RESET + "$%.2f%n",
        totalIncome);
    System.out.printf(
        ConsoleColors.BOLD_RED + "Total Expense: " + ConsoleColors.RESET + "$%.2f%n", totalExpense);
    System.out.println(
        ConsoleColors.BOLD_CYAN + "-------------------------------" + ConsoleColors.RESET);

    String balanceColor =
        balance > 0
            ? ConsoleColors.BOLD_GREEN
            : balance < 0 ? ConsoleColors.BOLD_RED : ConsoleColors.BOLD_YELLOW;
    System.out.printf(balanceColor + "Balance:       $%.2f%n" + ConsoleColors.RESET, balance);

    String status;
    if (balance > 0) {
      status = ConsoleColors.success("[OK] Positive balance");
    } else if (balance < 0) {
      status = ConsoleColors.error("[!] Negative balance");
    } else {
      status = ConsoleColors.warning("[=] Balanced");
    }
    System.out.println("\nStatus: " + status);
    InputUtils.pressAnyKeyToContinue(scanner);
  }
}
