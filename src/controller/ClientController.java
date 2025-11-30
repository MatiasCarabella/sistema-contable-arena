package controller;

import exception.ValidationException;
import java.util.List;
import model.Client;
import service.ClientService;

/**
 * Controller for client operations. Handles input validation before delegating to service layer.
 */
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  public Client createClient(String name, String cuit, String address, String phone) {
    validateClientData(name, cuit);
    return clientService.createClient(name, cuit, address, phone);
  }

  public Client updateClient(int id, String name, String cuit, String address, String phone) {
    validateId(id);
    validateClientData(name, cuit);
    return clientService.updateClient(id, name, cuit, address, phone);
  }

  public void deleteClient(int id) {
    validateId(id);
    clientService.deleteClient(id);
  }

  public Client findClient(int id) {
    validateId(id);
    return clientService.findClient(id);
  }

  public List<Client> listClients() {
    return clientService.listClients();
  }

  public List<Client> searchClients(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidationException("Search name cannot be empty");
    }
    return clientService.searchClients(name);
  }

  private void validateId(int id) {
    if (id <= 0) {
      throw new ValidationException("ID must be positive");
    }
  }

  private void validateClientData(String name, String cuit) {
    validateName(name, "Client");
    validateCuit(cuit);
  }

  private void validateName(String name, String entityType) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidationException(entityType + " name cannot be empty");
    }
  }

  private void validateCuit(String cuit) {
    if (cuit == null || cuit.trim().isEmpty()) {
      throw new ValidationException("CUIT cannot be empty");
    }
    if (!cuit.matches("\\d{2}-\\d{8}-\\d")) {
      throw new ValidationException("Invalid CUIT format. Expected: XX-XXXXXXXX-X");
    }
  }
}
