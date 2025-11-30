package view;

import java.util.Scanner;
import java.util.logging.Logger;
import repository.ClientRepository;
import repository.SupplierRepository;
import repository.TransactionRepository;
import service.ClientService;
import service.ReportService;
import service.SupplierService;
import service.TransactionService;

public class ConsoleMenu {
  private static final Logger LOGGER = Logger.getLogger(ConsoleMenu.class.getName());
  private final ClientService clientService;
  private final SupplierService supplierService;
  private final ReportService reportService;
  private final TransactionService transactionService;
  private final Scanner scanner;
  private final ClientsMenu clientsMenu;
  private final SuppliersMenu suppliersMenu;
  private final IncomesMenu incomesMenu;
  private final ExpensesMenu expensesMenu;
  private final ReportsMenu reportsMenu;

  public ConsoleMenu() {
    // Initialize repositories
    ClientRepository clientRepository = new ClientRepository();
    SupplierRepository supplierRepository = new SupplierRepository();
    TransactionRepository transactionRepository = new TransactionRepository();

    // Initialize services
    clientService = new ClientService(clientRepository);
    supplierService = new SupplierService(supplierRepository);
    transactionService =
        new TransactionService(transactionRepository, clientRepository, supplierRepository);
    reportService = new ReportService(transactionRepository);

    scanner = new Scanner(System.in);

    // Initialize menus
    clientsMenu = new ClientsMenu(clientService, scanner);
    suppliersMenu = new SuppliersMenu(supplierService, scanner);
    incomesMenu = new IncomesMenu(reportService, transactionService, clientService, scanner);
    expensesMenu = new ExpensesMenu(reportService, transactionService, supplierService, scanner);
    reportsMenu = new ReportsMenu(reportService, scanner);
  }

  public void start() {
    printBanner();
    int option;
    do {
      printMenu();
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "Select an option: " + ConsoleColors.RESET);
      handleOption(option);
    } while (option != 0);
    scanner.close();
    System.out.println(
        ConsoleColors.success("\n[OK] Goodbye! Thanks for using the Accounting System."));
  }

  private void printBanner() {
    System.out.println(ConsoleColors.BOLD_CYAN);
    System.out.println("===============================================================");
    System.out.println("                                                               ");
    System.out.println("      ###     ####   ####   ####  #   # #   # #####          ");
    System.out.println("     #   #   #      #      #    # #   # ##  #   #            ");
    System.out.println("     #####   #      #      #    # #   # # # #   #            ");
    System.out.println("     #   #   #      #      #    # #   # #  ##   #            ");
    System.out.println("     #   #    ####   ####   ####   ###  #   #   #            ");
    System.out.println("                                                               ");
    System.out.println(
        "              "
            + ConsoleColors.BOLD_WHITE
            + "ACCOUNTING MANAGEMENT SYSTEM"
            + ConsoleColors.BOLD_CYAN
            + "                  ");
    System.out.println("                                                               ");
    System.out.println("===============================================================");
    System.out.println(ConsoleColors.RESET);
  }

  private void printMenu() {
    System.out.println("\n" + ConsoleColors.BOLD_MAGENTA + "+============================+");
    System.out.println(
        "|       "
            + ConsoleColors.BOLD_WHITE
            + "MAIN MENU"
            + ConsoleColors.BOLD_MAGENTA
            + "          |");
    System.out.println("+============================+" + ConsoleColors.RESET);
    System.out.println(
        ConsoleColors.BOLD_MAGENTA
            + "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_BLUE
            + "1."
            + ConsoleColors.RESET
            + " Clients              "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println(
        "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_BLUE
            + "2."
            + ConsoleColors.RESET
            + " Suppliers            "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println(
        "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_BLUE
            + "3."
            + ConsoleColors.RESET
            + " Income               "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println(
        "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_BLUE
            + "4."
            + ConsoleColors.RESET
            + " Expenses             "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println(
        "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_BLUE
            + "5."
            + ConsoleColors.RESET
            + " Reports              "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println("+============================+");
    System.out.println(
        "|"
            + ConsoleColors.RESET
            + " "
            + ConsoleColors.BOLD_RED
            + "0."
            + ConsoleColors.RESET
            + " Exit                 "
            + ConsoleColors.BOLD_MAGENTA
            + "|");
    System.out.println("+============================+" + ConsoleColors.RESET);
  }

  private void handleOption(int option) {
    try {
      switch (option) {
        case 1 -> clientsMenu.show();
        case 2 -> suppliersMenu.show();
        case 3 -> incomesMenu.show();
        case 4 -> expensesMenu.show();
        case 5 -> reportsMenu.show();
        case 0 -> LOGGER.info("Exiting application");
        default ->
            System.out.println(ConsoleColors.warning("[!] Invalid option. Please try again."));
      }
    } catch (Exception e) {
      System.out.println(ConsoleColors.error("[ERROR] " + e.getMessage()));
      LOGGER.severe("Error handling menu option: " + e.getMessage());
    }
  }
}
