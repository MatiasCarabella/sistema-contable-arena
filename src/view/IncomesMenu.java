package view;

import controller.ReportController;
import controller.ClientController;
import model.Income;
import repository.TransactionRepository;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class IncomesMenu {
    private final ReportController reportController;
    private final TransactionRepository transactionRepo;
    private final ClientController clientController;
    private final Scanner scanner;

    public IncomesMenu(ReportController reportController, TransactionRepository transactionRepo, ClientController clientController, Scanner scanner) {
        this.reportController = reportController;
        this.transactionRepo = transactionRepo;
        this.clientController = clientController;
        this.scanner = scanner;
    }

    public void mostrar() {
        int op;
        do {
            System.out.println("\n--- Gestión de Ingresos ---");
            System.out.println("1. Registrar ingreso asociado a cliente");
            System.out.println("2. Eliminar ingreso");
            System.out.println("3. Listar ingresos de un cliente");
            System.out.println("0. Volver");
            op = InputUtils.readInt(scanner, "Seleccione una opción: ");
            switch (op) {
                case 1 -> registerIncome();
                case 2 -> deleteIncome();
                case 3 -> listIncomesByClient();
                case 0 -> {}
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void registerIncome() {
        System.out.println("--- Registrar ingreso asociado a cliente ---");
        int clientId = InputUtils.readInt(scanner, "ID del cliente: ");
        if (clientController.findClient(clientId) == null) {
            System.out.println("Error: El cliente no existe.");
            return;
        }
        double amount = InputUtils.readDouble(scanner, "Monto: ");
        String desc = InputUtils.readString(scanner, "Descripción: ");
        LocalDate date = InputUtils.readDate(scanner, "Fecha (YYYY-MM-DD): ");
        Income income = new Income(0, date, amount, desc, clientId);
        transactionRepo.save(income);
        System.out.println("Ingreso registrado");
    }

    private void deleteIncome() {
        System.out.println("--- Eliminar ingreso ---");
        int id = InputUtils.readInt(scanner, "ID del ingreso: ");
        transactionRepo.delete(id);
        System.out.println("Ingreso eliminado (si existía)");
    }

    private void listIncomesByClient() {
        System.out.println("--- Ingresos de un cliente ---");
        int clientId = InputUtils.readInt(scanner, "ID del cliente: ");
        List<Income> incomes = reportController.getIncomesByClient(clientId);
        for (Income i : incomes) {
            System.out.println(i.getId() + ": $" + i.getAmount() + " | " + i.getDescription() + " | " + i.getDate());
        }
        if (incomes.isEmpty()) System.out.println("No hay ingresos para este cliente");
    }
}
