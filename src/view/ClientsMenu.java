package view;

import controller.ClientController;
import model.Client;
import java.util.List;
import java.util.Scanner;

public class ClientsMenu {
    private final ClientController clientController;
    private final Scanner scanner;

    public ClientsMenu(ClientController clientController, Scanner scanner) {
        this.clientController = clientController;
        this.scanner = scanner;
    }

    public void mostrar() {
        int op;
        do {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Actualizar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Buscar cliente");
            System.out.println("5. Listar todos los clientes");
            System.out.println("0. Volver");
            op = InputUtils.readInt(scanner, "Seleccione una opción: ");
            switch (op) {
                case 1 -> registerClient();
                case 2 -> updateClient();
                case 3 -> deleteClient();
                case 4 -> searchClient();
                case 5 -> listClients();
                case 0 -> {}
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void registerClient() {
        System.out.println("--- Registrar nuevo cliente ---");
        String name = InputUtils.readString(scanner, "Nombre: ");
        String cuit = InputUtils.readString(scanner, "CUIT: ");
        String address = InputUtils.readString(scanner, "Dirección: ");
        String phone = InputUtils.readString(scanner, "Teléfono: ");
        Client c = clientController.registerClient(name, cuit, address, phone);
        System.out.println("Cliente registrado con ID: " + c.getId());
    }

    private void updateClient() {
        System.out.println("--- Actualizar cliente ---");
        int id = InputUtils.readInt(scanner, "ID del cliente: ");
        String name = InputUtils.readString(scanner, "Nuevo nombre: ");
        String cuit = InputUtils.readString(scanner, "Nuevo CUIT: ");
        String address = InputUtils.readString(scanner, "Nueva dirección: ");
        String phone = InputUtils.readString(scanner, "Nuevo teléfono: ");
        Client c = clientController.updateClient(id, name, cuit, address, phone);
        if (c != null) System.out.println("Cliente actualizado");
        else System.out.println("Cliente no encontrado");
    }

    private void deleteClient() {
        System.out.println("--- Eliminar cliente ---");
        int id = InputUtils.readInt(scanner, "ID del cliente: ");
        clientController.deleteClient(id);
        System.out.println("Cliente eliminado (si existía)");
    }

    private void searchClient() {
        System.out.println("--- Buscar cliente ---");
        String name = InputUtils.readString(scanner, "Nombre a buscar: ");
        List<Client> found = clientController.searchClients(name);
        for (Client c : found) {
            System.out.println(c.getId() + ": " + c.getName() + " | CUIT: " + c.getCuit());
        }
        if (found.isEmpty()) System.out.println("No se encontraron clientes");
    }

    private void listClients() {
        System.out.println("--- Lista de clientes ---");
        for (Client c : clientController.listClients()) {
            System.out.println(c.getId() + ": " + c.getName() + " | CUIT: " + c.getCuit());
        }
    }
}
