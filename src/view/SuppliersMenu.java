package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import java.util.List;
import java.util.Scanner;
import model.Supplier;
import service.SupplierService;

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
      System.out.println("\n" + ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.title("|  ")
              + ConsoleColors.BOLD_WHITE
              + "SUPPLIER MANAGEMENT"
              + ConsoleColors.title("     |"));
      System.out.println(ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.BLUE + "1." + ConsoleColors.RESET + " Register new supplier");
      System.out.println(ConsoleColors.BLUE + "2." + ConsoleColors.RESET + " Update supplier");
      System.out.println(ConsoleColors.BLUE + "3." + ConsoleColors.RESET + " Delete supplier");
      System.out.println(ConsoleColors.BLUE + "4." + ConsoleColors.RESET + " Search supplier");
      System.out.println(ConsoleColors.BLUE + "5." + ConsoleColors.RESET + " List all suppliers");
      System.out.println(ConsoleColors.RED + "0." + ConsoleColors.RESET + " Back");
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "\nSelect an option: " + ConsoleColors.RESET);
      try {
        switch (option) {
          case 1 -> registerSupplier();
          case 2 -> updateSupplier();
          case 3 -> deleteSupplier();
          case 4 -> searchSupplier();
          case 5 -> listSuppliers();
          case 0 -> {}
          default -> System.out.println(ConsoleColors.warning("[!] Invalid option"));
        }
      } catch (ValidationException | EntityNotFoundException e) {
        System.out.println(ConsoleColors.error("[ERROR] " + e.getMessage()));
      }
    } while (option != 0);
  }

  private void registerSupplier() {
    System.out.println("\n" + ConsoleColors.info("=== Register New Supplier ==="));
    String name = InputUtils.readString(scanner, "Name: ");
    String cuit = InputUtils.readString(scanner, "CUIT (XX-XXXXXXXX-X): ");
    String address = InputUtils.readString(scanner, "Address: ");
    String phone = InputUtils.readString(scanner, "Phone: ");
    Supplier supplier = supplierService.createSupplier(name, cuit, address, phone);
    System.out.println(
        ConsoleColors.success("\n[OK] Supplier registered with ID: " + supplier.getId()));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void updateSupplier() {
    System.out.println("\n" + ConsoleColors.info("=== Update Supplier ==="));
    int id = InputUtils.readInt(scanner, "Supplier ID: ");
    String name = InputUtils.readString(scanner, "New name: ");
    String cuit = InputUtils.readString(scanner, "New CUIT: ");
    String address = InputUtils.readString(scanner, "New address: ");
    String phone = InputUtils.readString(scanner, "New phone: ");
    Supplier supplier = supplierService.updateSupplier(id, name, cuit, address, phone);
    System.out.println(ConsoleColors.success("\n[OK] Supplier updated successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void deleteSupplier() {
    System.out.println("\n" + ConsoleColors.info("=== Delete Supplier ==="));
    int id = InputUtils.readInt(scanner, "Supplier ID: ");
    supplierService.deleteSupplier(id);
    System.out.println(ConsoleColors.success("\n[OK] Supplier deleted successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void searchSupplier() {
    System.out.println("\n" + ConsoleColors.info("=== Search Supplier ==="));
    String name = InputUtils.readString(scanner, "Name to search: ");
    List<Supplier> found = supplierService.searchSuppliers(name);
    if (found.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No suppliers found"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_GREEN
              + "\nFound "
              + found.size()
              + " supplier(s):"
              + ConsoleColors.RESET);
      for (Supplier s : found) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " %s | CUIT: %s | Phone: %s%n",
            s.getId(),
            s.getName(),
            s.getCuit(),
            s.getPhone());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void listSuppliers() {
    System.out.println("\n" + ConsoleColors.info("=== Supplier List ==="));
    List<Supplier> suppliers = supplierService.listSuppliers();
    if (suppliers.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No suppliers registered"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_GREEN
              + "\nTotal suppliers: "
              + suppliers.size()
              + ConsoleColors.RESET);
      for (Supplier s : suppliers) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " %s | CUIT: %s | Phone: %s%n",
            s.getId(),
            s.getName(),
            s.getCuit(),
            s.getPhone());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }
}
