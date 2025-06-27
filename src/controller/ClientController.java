package controller;

import model.Client;
import repository.ClientRepository;
import java.util.List;

public class ClientController {
    private ClientRepository clientRepository;

    public ClientController(ClientRepository repository) {
        this.clientRepository = repository;
    }

    public Client createClient(String name, String cuit, String address, String phone) {
        Client client = new Client(0, name, cuit, address, phone);
        return clientRepository.create(client);
    }

    public Client updateClient(int id, String name, String cuit, String address, String phone) {
        Client client = clientRepository.findById(id);
        if (client != null) {
            client.setName(name);
            client.setCuit(cuit);
            client.setAddress(address);
            client.setPhone(phone);
            clientRepository.update(client);
        }
        return client;
    }

    public void deleteClient(int id) {
        clientRepository.delete(id);
    }

    public Client findClient(int id) {
        return clientRepository.findById(id);
    }

    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    public List<Client> searchClients(String name) {
        return clientRepository.searchByName(name);
    }
}
