package service;

import exception.EntityNotFoundException;
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
    final Client client = new Client(0, name, cuit, address, phone);
    return clientRepository.save(client);
  }

  public Client updateClient(int id, String name, String cuit, String address, String phone) {
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
    return clientRepository.searchByName(name);
  }
}
