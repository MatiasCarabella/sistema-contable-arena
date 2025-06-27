package view;

import controller.ClientController;
import controller.SupplierController;
import controller.ReportController;
import controller.TransactionController;
import repository.ClientRepository;
import repository.SupplierRepository;
import repository.TransactionRepository;
import java.util.Scanner;

public class ConsoleMenu {
    private final ClientController clientController;
    private final SupplierController supplierController;
    private final ReportController reportController;
    private final TransactionController transactionController;
    private final Scanner scanner;
    private final ClientsMenu clientsMenu;
    private final SuppliersMenu suppliersMenu;
    private final IncomesMenu incomesMenu;
    private final ExpensesMenu expensesMenu;
    private final ReportsMenu reportsMenu;

    public ConsoleMenu() {
        ClientRepository clientRepository = new ClientRepository();
        SupplierRepository supplierRepository = new SupplierRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        clientController = new ClientController(clientRepository);
        supplierController = new SupplierController(supplierRepository);
        transactionController = new TransactionController(transactionRepository);
        reportController = new ReportController(transactionRepository);
        scanner = new Scanner(System.in);
        clientsMenu = new ClientsMenu(clientController, scanner);
        suppliersMenu = new SuppliersMenu(supplierController, scanner);
        incomesMenu = new IncomesMenu(reportController, transactionController, clientController, scanner);
        expensesMenu = new ExpensesMenu(reportController, transactionController, supplierController, scanner);
        reportsMenu = new ReportsMenu(reportController, scanner);
    }

    public void start() {
        int option;
        do {
            printMenu();
            option = InputUtils.readInt(scanner, "Seleccione una opción: ");
            handleOption(option);
        } while (option != 0);
    }

    private void printMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Clientes");
        System.out.println("2. Proveedores");
        System.out.println("3. Ingresos");
        System.out.println("4. Gastos");
        System.out.println("5. Reportes");
        System.out.println("0. Salir");
    }

    private void handleOption(int option) {
        switch (option) {
            case 1 -> clientsMenu.mostrar();
            case 2 -> suppliersMenu.mostrar();
            case 3 -> incomesMenu.mostrar();
            case 4 -> expensesMenu.mostrar();
            case 5 -> reportsMenu.mostrar();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida");
        }
    }
}
