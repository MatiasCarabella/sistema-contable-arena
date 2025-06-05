package view;

import controller.SupplierController;
import model.Supplier;
import java.util.List;
import java.util.Scanner;

public class SuppliersMenu {
    private final SupplierController supplierController;
    private final Scanner scanner;

    public SuppliersMenu(SupplierController supplierController, Scanner scanner) {
        this.supplierController = supplierController;
        this.scanner = scanner;
    }

    public void mostrar() {
        int op;
        do {
            System.out.println("\n--- Gestión de Proveedores ---");
            System.out.println("1. Registrar nuevo proveedor");
            System.out.println("2. Actualizar proveedor");
            System.out.println("3. Eliminar proveedor");
            System.out.println("4. Buscar proveedor");
            System.out.println("5. Listar todos los proveedores");
            System.out.println("0. Volver");
            op = InputUtils.readInt(scanner, "Seleccione una opción: ");
            switch (op) {
                case 1 -> registerSupplier();
                case 2 -> updateSupplier();
                case 3 -> deleteSupplier();
                case 4 -> searchSupplier();
                case 5 -> listSuppliers();
                case 0 -> {}
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void registerSupplier() {
        System.out.println("--- Registrar nuevo proveedor ---");
        String name = InputUtils.readString(scanner, "Nombre: ");
        String cuit = InputUtils.readString(scanner, "CUIT: ");
        String address = InputUtils.readString(scanner, "Dirección: ");
        String phone = InputUtils.readString(scanner, "Teléfono: ");
        Supplier s = supplierController.registerSupplier(name, cuit, address, phone);
        System.out.println("Proveedor registrado con ID: " + s.getId());
    }

    private void updateSupplier() {
        System.out.println("--- Actualizar proveedor ---");
        int id = InputUtils.readInt(scanner, "ID del proveedor: ");
        String name = InputUtils.readString(scanner, "Nuevo nombre: ");
        String cuit = InputUtils.readString(scanner, "Nuevo CUIT: ");
        String address = InputUtils.readString(scanner, "Nueva dirección: ");
        String phone = InputUtils.readString(scanner, "Nuevo teléfono: ");
        Supplier s = supplierController.updateSupplier(id, name, cuit, address, phone);
        if (s != null) System.out.println("Proveedor actualizado");
        else System.out.println("Proveedor no encontrado");
    }

    private void deleteSupplier() {
        System.out.println("--- Eliminar proveedor ---");
        int id = InputUtils.readInt(scanner, "ID del proveedor: ");
        supplierController.deleteSupplier(id);
        System.out.println("Proveedor eliminado (si existía)");
    }

    private void searchSupplier() {
        System.out.println("--- Buscar proveedor ---");
        String name = InputUtils.readString(scanner, "Nombre a buscar: ");
        List<Supplier> found = supplierController.searchSuppliers(name);
        for (Supplier s : found) {
            System.out.println(s.getId() + ": " + s.getName() + " | CUIT: " + s.getCuit());
        }
        if (found.isEmpty()) System.out.println("No se encontraron proveedores");
    }

    private void listSuppliers() {
        System.out.println("--- Lista de proveedores ---");
        for (Supplier s : supplierController.listSuppliers()) {
            System.out.println(s.getId() + ": " + s.getName() + " | CUIT: " + s.getCuit());
        }
    }
}
