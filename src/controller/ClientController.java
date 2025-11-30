package controller;

import model.Client;
import service.ClientService;
import java.util.List;

/**
 * @deprecated Use {@link ClientService} directly instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client createClient(String name, String cuit, String address, String phone) {
        return clientService.createClient(name, cuit, address, phone);
    }

    public Client updateClient(int id, String name, String cuit, String address, String phone) {
        return clientService.updateClient(id, name, cuit, address, phone);
    }

    public void deleteClient(int id) {
        clientService.deleteClient(id);
    }

    public Client findClient(int id) {
        return clientService.findClient(id);
    }

    public List<Client> listClients() {
        return clientService.listClients();
    }

    public List<Client> searchClients(String name) {
        return clientService.searchClients(name);
    }
}
