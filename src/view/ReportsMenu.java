package view;

import controller.ReportController;
import model.Transaction;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class ReportsMenu {
    private final ReportController reportController;
    private final Scanner scanner;

    public ReportsMenu(ReportController reportController, Scanner scanner) {
        this.reportController = reportController;
        this.scanner = scanner;
    }

    public void mostrar() {
        int op;
        do {
            System.out.println("\n--- Reportes ---");
            System.out.println("1. Consultar historial de ingresos y gastos");
            System.out.println("2. Generar reporte de balance por rango de fechas");
            System.out.println("0. Volver");
            op = InputUtils.readInt(scanner, "Seleccione una opción: ");
            switch (op) {
                case 1 -> listAllTransactions();
                case 2 -> generateBalanceReport();
                case 0 -> {}
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void listAllTransactions() {
        System.out.println("--- Historial de ingresos y gastos ---");
        for (Transaction t : reportController.getTransactionsBetween(LocalDate.MIN, LocalDate.MAX)) {
            String tipo = t.getClass().getSimpleName();
            System.out.println(t.getId() + ": [" + tipo + "] $" + t.getAmount() + " | " + t.getDescription() + " | " + t.getDate());
        }
    }

    private void generateBalanceReport() {
        System.out.println("--- Reporte de balance por rango de fechas ---");
        LocalDate from = InputUtils.readDate(scanner, "Desde (YYYY-MM-DD): ");
        LocalDate to = InputUtils.readDate(scanner, "Hasta (YYYY-MM-DD): ");
        List<Transaction> txs = reportController.getTransactionsBetween(from, to);
        double ingresos = 0, egresos = 0;
        for (Transaction t : txs) {
            if (t.getClass().getSimpleName().equals("Income")) ingresos += t.getAmount();
            if (t.getClass().getSimpleName().equals("Expense")) egresos += t.getAmount();
        }
        System.out.println("Ingresos: $" + ingresos);
        System.out.println("Egresos: $" + egresos);
        System.out.println("Balance: $" + (ingresos - egresos));
    }
}
