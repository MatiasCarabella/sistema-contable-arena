package view;

import exception.EntityNotFoundException;
import exception.ValidationException;
import java.util.List;
import java.util.Scanner;
import model.Client;
import service.ClientService;

public class ClientsMenu {
  private final ClientService clientService;
  private final Scanner scanner;

  public ClientsMenu(ClientService clientService, Scanner scanner) {
    this.clientService = clientService;
    this.scanner = scanner;
  }

  public void show() {
    int option;
    do {
      System.out.println("\n" + ConsoleColors.title("+-----------------------------+"));
      System.out.println(
          ConsoleColors.title("|   ")
              + ConsoleColors.BOLD_WHITE
              + "CLIENT MANAGEMENT"
              + ConsoleColors.title("      |"));
      System.out.println(ConsoleColors.title("+-----------------------------+"));
      System.out.println(ConsoleColors.BLUE + "1." + ConsoleColors.RESET + " Register new client");
      System.out.println(ConsoleColors.BLUE + "2." + ConsoleColors.RESET + " Update client");
      System.out.println(ConsoleColors.BLUE + "3." + ConsoleColors.RESET + " Delete client");
      System.out.println(ConsoleColors.BLUE + "4." + ConsoleColors.RESET + " Search client");
      System.out.println(ConsoleColors.BLUE + "5." + ConsoleColors.RESET + " List all clients");
      System.out.println(ConsoleColors.RED + "0." + ConsoleColors.RESET + " Back");
      option =
          InputUtils.readInt(
              scanner, ConsoleColors.CYAN + "\nSelect an option: " + ConsoleColors.RESET);
      try {
        switch (option) {
          case 1 -> createClient();
          case 2 -> updateClient();
          case 3 -> deleteClient();
          case 4 -> searchClient();
          case 5 -> listClients();
          case 0 -> {}
          default -> System.out.println(ConsoleColors.warning("[!] Invalid option"));
        }
      } catch (ValidationException | EntityNotFoundException e) {
        System.out.println(ConsoleColors.error("[ERROR] " + e.getMessage()));
      }
    } while (option != 0);
  }

  private void createClient() {
    System.out.println("\n" + ConsoleColors.info("=== Register New Client ==="));
    String name = InputUtils.readString(scanner, "Name: ");
    String cuit = InputUtils.readString(scanner, "CUIT (XX-XXXXXXXX-X): ");
    String address = InputUtils.readString(scanner, "Address: ");
    String phone = InputUtils.readString(scanner, "Phone: ");
    Client client = clientService.createClient(name, cuit, address, phone);
    System.out.println(
        ConsoleColors.success("\n[OK] Client registered with ID: " + client.getId()));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void updateClient() {
    System.out.println("\n" + ConsoleColors.info("=== Update Client ==="));
    int id = InputUtils.readInt(scanner, "Client ID: ");
    String name = InputUtils.readString(scanner, "New name: ");
    String cuit = InputUtils.readString(scanner, "New CUIT: ");
    String address = InputUtils.readString(scanner, "New address: ");
    String phone = InputUtils.readString(scanner, "New phone: ");
    Client client = clientService.updateClient(id, name, cuit, address, phone);
    System.out.println(ConsoleColors.success("\n[OK] Client updated successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void deleteClient() {
    System.out.println("\n" + ConsoleColors.info("=== Delete Client ==="));
    int id = InputUtils.readInt(scanner, "Client ID: ");
    clientService.deleteClient(id);
    System.out.println(ConsoleColors.success("\n[OK] Client deleted successfully"));
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void searchClient() {
    System.out.println("\n" + ConsoleColors.info("=== Search Client ==="));
    String name = InputUtils.readString(scanner, "Name to search: ");
    List<Client> found = clientService.searchClients(name);
    if (found.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No clients found"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_GREEN
              + "\nFound "
              + found.size()
              + " client(s):"
              + ConsoleColors.RESET);
      for (Client c : found) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " %s | CUIT: %s | Phone: %s%n",
            c.getId(),
            c.getName(),
            c.getCuit(),
            c.getPhone());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }

  private void listClients() {
    System.out.println("\n" + ConsoleColors.info("=== Client List ==="));
    List<Client> clients = clientService.listClients();
    if (clients.isEmpty()) {
      System.out.println(ConsoleColors.warning("\n[!] No clients registered"));
    } else {
      System.out.println(
          ConsoleColors.BOLD_GREEN + "\nTotal clients: " + clients.size() + ConsoleColors.RESET);
      for (Client c : clients) {
        System.out.printf(
            ConsoleColors.BOLD_BLUE
                + "ID %d:"
                + ConsoleColors.RESET
                + " %s | CUIT: %s | Phone: %s%n",
            c.getId(),
            c.getName(),
            c.getCuit(),
            c.getPhone());
      }
    }
    InputUtils.pressAnyKeyToContinue(scanner);
  }
}
