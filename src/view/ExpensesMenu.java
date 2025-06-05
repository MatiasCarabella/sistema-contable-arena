package view;

import controller.ReportController;
import controller.SupplierController;
import model.Expense;
import repository.TransactionRepository;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class ExpensesMenu {
    private final ReportController reportController;
    private final TransactionRepository transactionRepo;
    private final SupplierController supplierController;
    private final Scanner scanner;

    public ExpensesMenu(ReportController reportController, TransactionRepository transactionRepo, SupplierController supplierController, Scanner scanner) {
        this.reportController = reportController;
        this.transactionRepo = transactionRepo;
        this.supplierController = supplierController;
        this.scanner = scanner;
    }

    public void mostrar() {
        int op;
        do {
            System.out.println("\n--- Gestión de Gastos ---");
            System.out.println("1. Registrar gasto asociado a proveedor");
            System.out.println("2. Eliminar gasto");
            System.out.println("3. Listar gastos de un proveedor");
            System.out.println("0. Volver");
            op = InputUtils.readInt(scanner, "Seleccione una opción: ");
            switch (op) {
                case 1 -> registerExpense();
                case 2 -> deleteExpense();
                case 3 -> listExpensesBySupplier();
                case 0 -> {}
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void registerExpense() {
        System.out.println("--- Registrar gasto asociado a proveedor ---");
        int supplierId = InputUtils.readInt(scanner, "ID del proveedor: ");
        if (supplierController.findSupplier(supplierId) == null) {
            System.out.println("Error: El proveedor no existe.");
            return;
        }
        double amount = InputUtils.readDouble(scanner, "Monto: ");
        String desc = InputUtils.readString(scanner, "Descripción: ");
        LocalDate date = InputUtils.readDate(scanner, "Fecha (YYYY-MM-DD): ");
        Expense expense = new Expense(0, date, amount, desc, supplierId);
        transactionRepo.save(expense);
        System.out.println("Gasto registrado");
    }

    private void deleteExpense() {
        System.out.println("--- Eliminar gasto ---");
        int id = InputUtils.readInt(scanner, "ID del gasto: ");
        transactionRepo.delete(id);
        System.out.println("Gasto eliminado (si existía)");
    }

    private void listExpensesBySupplier() {
        System.out.println("--- Gastos de un proveedor ---");
        int supplierId = InputUtils.readInt(scanner, "ID del proveedor: ");
        List<Expense> expenses = reportController.getExpensesBySupplier(supplierId);
        for (Expense e : expenses) {
            System.out.println(e.getId() + ": $" + e.getAmount() + " | " + e.getDescription() + " | " + e.getDate());
        }
        if (expenses.isEmpty()) System.out.println("No hay gastos para este proveedor");
    }
}
