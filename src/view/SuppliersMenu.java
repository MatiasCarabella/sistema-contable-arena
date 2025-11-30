package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import model.Supplier;
import service.SupplierService;
import java.util.List;
import java.util.Scanner;

public class SuppliersMenu {
    private final SupplierService supplierService;
    private final Scanner scanner;

    public SuppliersMenu(SupplierService supplierService, Scanner scanner) {
        this.supplierService = supplierService;
        this.scanner = scanner;
    }

    public void show() {
        int option;
        do {
            System.out.println("\n--- Supplier Management ---");
            System.out.println("1. Register new supplier");
            System.out.println("2. Update supplier");
            System.out.println("3. Delete supplier");
            System.out.println("4. Search supplier");
            System.out.println("5. List all suppliers");
            System.out.println("0. Back");
            option = InputUtils.readInt(scanner, "Select an option: ");
            try {
                switch (option) {
                    case 1 -> registerSupplier();
                    case 2 -> updateSupplier();
                    case 3 -> deleteSupplier();
                    case 4 -> searchSupplier();
                    case 5 -> listSuppliers();
                    case 0 -> {}
                    default -> System.out.println("Invalid option");
                }
            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void registerSupplier() {
        System.out.println("--- Register New Supplier ---");
        String name = InputUtils.readString(scanner, "Name: ");
        String cuit = InputUtils.readString(scanner, "CUIT (XX-XXXXXXXX-X): ");
        String address = InputUtils.readString(scanner, "Address: ");
        String phone = InputUtils.readString(scanner, "Phone: ");
        Supplier supplier = supplierService.createSupplier(name, cuit, address, phone);
        System.out.println("Supplier registered with ID: " + supplier.getId());
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void updateSupplier() {
        System.out.println("--- Update Supplier ---");
        int id = InputUtils.readInt(scanner, "Supplier ID: ");
        String name = InputUtils.readString(scanner, "New name: ");
        String cuit = InputUtils.readString(scanner, "New CUIT: ");
        String address = InputUtils.readString(scanner, "New address: ");
        String phone = InputUtils.readString(scanner, "New phone: ");
        Supplier supplier = supplierService.updateSupplier(id, name, cuit, address, phone);
        System.out.println("Supplier updated successfully");
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void deleteSupplier() {
        System.out.println("--- Delete Supplier ---");
        int id = InputUtils.readInt(scanner, "Supplier ID: ");
        supplierService.deleteSupplier(id);
        System.out.println("Supplier deleted successfully");
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void searchSupplier() {
        System.out.println("--- Search Supplier ---");
        String name = InputUtils.readString(scanner, "Name to search: ");
        List<Supplier> found = supplierService.searchSuppliers(name);
        if (found.isEmpty()) {
            System.out.println("No suppliers found");
        } else {
            for (Supplier s : found) {
                System.out.printf("%d: %s | CUIT: %s | Phone: %s%n", 
                    s.getId(), s.getName(), s.getCuit(), s.getPhone());
            }
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }

    private void listSuppliers() {
        System.out.println("--- Supplier List ---");
        List<Supplier> suppliers = supplierService.listSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers registered");
        } else {
            for (Supplier s : suppliers) {
                System.out.printf("%d: %s | CUIT: %s | Phone: %s%n", 
                    s.getId(), s.getName(), s.getCuit(), s.getPhone());
            }
        }
        InputUtils.pressAnyKeyToContinue(scanner);
    }
}
