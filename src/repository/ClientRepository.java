package repository;

import model.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository {
    private Map<Integer, Client> clients = new HashMap<>();
    private int nextId = 1;

    public Client save(Client client) {
        if (client.getId() == 0) {
            client.setId(nextId++);
        }
        clients.put(client.getId(), client);
        return client;
    }

    public Client findById(int id) {
        return clients.get(id);
    }

    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }

    public void delete(int id) {
        clients.remove(id);
    }

    public List<Client> searchByName(String name) {
        List<Client> result = new ArrayList<>();
        for (Client c : clients.values()) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }
}
