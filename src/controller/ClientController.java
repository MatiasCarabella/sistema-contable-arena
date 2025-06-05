package controller;

import model.Client;
import repository.ClientRepository;
import java.util.List;

public class ClientController {
    private ClientRepository clientRepo;

    public ClientController(ClientRepository repo) {
        this.clientRepo = repo;
    }

    public Client registerClient(String name, String cuit, String address, String phone) {
        Client client = new Client(0, name, cuit, address, phone);
        return clientRepo.save(client);
    }

    public Client updateClient(int id, String name, String cuit, String address, String phone) {
        Client client = clientRepo.findById(id);
        if (client != null) {
            client.setName(name);
            client.setCuit(cuit);
            client.setAddress(address);
            client.setPhone(phone);
            clientRepo.save(client);
        }
        return client;
    }

    public void deleteClient(int id) {
        clientRepo.delete(id);
    }

    public Client findClient(int id) {
        return clientRepo.findById(id);
    }

    public List<Client> listClients() {
        return clientRepo.findAll();
    }

    public List<Client> searchClients(String name) {
        return clientRepo.searchByName(name);
    }
}
