package service;

import exception.EntityNotFoundException;
import exception.ValidationException;
import java.util.List;
import java.util.logging.Logger;
import model.Client;
import repository.ClientRepository;

public class ClientService {
  private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Client createClient(String name, String cuit, String address, String phone) {
    validateClientData(name, cuit);
    final Client client = new Client(0, name, cuit, address, phone);
    return clientRepository.save(client);
  }

  public Client updateClient(int id, String name, String cuit, String address, String phone) {
    validateClientData(name, cuit);
    final Client client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));

    client.setName(name);
    client.setCuit(cuit);
    client.setAddress(address);
    client.setPhone(phone);
    return clientRepository.save(client);
  }

  public void deleteClient(int id) {
    clientRepository.deleteById(id);
    LOGGER.info("Client deleted with ID: " + id);
  }

  public Client findClient(int id) {
    return clientRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));
  }

  public List<Client> listClients() {
    return clientRepository.findAll();
  }

  public List<Client> searchClients(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidationException("Search name cannot be empty");
    }
    return clientRepository.searchByName(name);
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
