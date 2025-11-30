package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Income;
import service.ClientService;
import service.ReportService;
import service.TransactionService;

public class IncomesMenu {
  private final ReportService reportService;
  private final TransactionService transactionService;
  private final ClientService clientService;
  private final Scanner scanner;

  public IncomesMenu(
      ReportService reportService,
      TransactionService transactionService,
      ClientService clientService,
      Scanner scanner) {
    this.reportService = reportService;
    this.transactionService = transactionService;
    this.clientService = clientService;
    this.scanner = scanner;
  }

  public void show() {
    int option;
    do {
      System.out.println("\n" + ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.title("|   ")
              + ConsoleColors.BOLD_WHITE
              + "INCOME MANAGEMENT"
              + ConsoleColors.title("      |"));
      System.out.println(ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.BOLD_GREEN + "1." + ConsoleColors.RESET + " Register income");
      System.out.println(ConsoleColors.BLUE + "2." + ConsoleColors.RESET + " Delete income");
      System.out.println(
          ConsoleColors.BLUE + "3." + ConsoleColors.RESET + " List income by client");
      System.out.println(ConsoleColors.RED + "0." + ConsoleColors.RESET + " Back");
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "\nSelect an option: " + ConsoleColors.RESET);
      try {
        switch (option) {
          case 1 -> registerIncome();
          case 2 -> deleteIncome();
          case 3 -> listIncomesByClient();
          case 0 -> {}
          default -> System.out.println(ConsoleColors.warning("[!] Invalid option"));
        }
      } catch (ValidationException | EntityNotFoundException e) {
        System.out.println(ConsoleColors.error("[ERROR] " + e.getMessage()));
      }
    } while (option != 0);
  }

  private void registerIncome() {
    System.out.println("\n" + ConsoleColors.info("=== Register Income ==="));
    int clientId = InputUtils.readInt(scanner, "Client ID: ");
    double amount = InputUtils.readDouble(scanner, "Amount: $");
    String description = InputUtils.readString(scanner, "Description: ");
    LocalDate date = InputUtils.readDate(scanner, "Date (YYYY-MM-DD): ");

    Income income = new Income(0, date, amount, description, clientId);
    transactionService.createIncome(income);
    System.out.println(
        ConsoleColors.success("\n[OK] Income registered with ID: " + income.getId()));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void deleteIncome() {
    System.out.println("\n" + ConsoleColors.info("=== Delete Income ==="));
    int id = InputUtils.readInt(scanner, "Income ID: ");
    transactionService.deleteTransaction(id);
    System.out.println(ConsoleColors.success("\n[OK] Income deleted successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void listIncomesByClient() {
    System.out.println("\n" + ConsoleColors.info("=== Income by Client ==="));
    int clientId = InputUtils.readInt(scanner, "Client ID: ");
    List<Income> incomes = reportService.getIncomesByClient(clientId);

    if (incomes.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No income found for this client"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_GREEN + "\nTotal records: " + incomes.size() + ConsoleColors.RESET);
      for (Income income : incomes) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " "
                + ConsoleColors.BOLD_GREEN
                + "$%.2f"
                + ConsoleColors.RESET
                + " | %s | %s%n",
            income.getId(),
            income.getAmount(),
            income.getDescription(),
            income.getDate());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }
}
