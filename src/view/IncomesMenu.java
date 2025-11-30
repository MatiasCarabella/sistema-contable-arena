package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import model.Income;
import service.ClientService;
import service.ReportService;
import service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class IncomesMenu {
    private final ReportService reportService;
    private final TransactionService transactionService;
    private final ClientService clientService;
    private final Scanner scanner;

    public IncomesMenu(ReportService reportService, TransactionService transactionService, 
                      ClientService clientService, Scanner scanner) {
        this.reportService = reportService;
        this.transactionService = transactionService;
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void show() {
        int option;
        do {
            System.out.println("\n--- Income Management ---");
            System.out.println("1. Register income associated with client");
            System.out.println("2. Delete income");
            System.out.println("3. List income by client");
            System.out.println("0. Back");
            option = InputUtils.readInt(scanner, "Select an option: ");
            try {
                switch (option) {
                    case 1 -> registerIncome();
                    case 2 -> deleteIncome();
                    case 3 -> listIncomesByClient();
                    case 0 -> {}
                    default -> System.out.println("Invalid option");
                }
            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void registerIncome() {
        System.out.println("--- Register Income Associated with Client ---");
        int clientId = InputUtils.readInt(scanner, "Client ID: ");
        double amount = InputUtils.readDouble(scanner, "Amount: ");
        String description = InputUtils.readString(scanner, "Description: ");
        LocalDate date = InputUtils.readDate(scanner, "Date (YYYY-MM-DD): ");
        
        Income income = new Income(0, date, amount, description, clientId);
        transactionService.createIncome(income);
        System.out.println("Income registered successfully with ID: " + income.getId());
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void deleteIncome() {
        System.out.println("--- Delete Income ---");
        int id = InputUtils.readInt(scanner, "Income ID: ");
        transactionService.deleteTransaction(id);
        System.out.println("Income deleted successfully");
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void listIncomesByClient() {
        System.out.println("--- Income by Client ---");
        int clientId = InputUtils.readInt(scanner, "Client ID: ");
        List<Income> incomes = reportService.getIncomesByClient(clientId);
        
        if (incomes.isEmpty()) {
            System.out.println("No income found for this client");
        } else {
            System.out.println("\nIncome records:");
            for (Income income : incomes) {
                System.out.printf("ID: %d | Amount: $%.2f | %s | Date: %s%n",
                    income.getId(), income.getAmount(), income.getDescription(), income.getDate());
            }
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }
}
