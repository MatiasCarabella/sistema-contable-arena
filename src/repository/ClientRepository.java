package repository;

import config.DatabaseConfig;
import exception.DatabaseException;
import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ClientRepository implements Repository<Client, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ClientRepository.class.getName());
    @Override
    public Client save(Client client) {
        if (client.getId() == 0) {
            return create(client);
        } else {
            return update(client);
        }
    }

    private Client create(Client client) {
        String sql = "INSERT INTO clients (name, cuit, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCuit());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getPhone());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
            }
            LOGGER.info("Client created with ID: " + client.getId());
            return client;
        } catch (SQLException e) {
            LOGGER.severe("Error creating client: " + e.getMessage());
            throw new DatabaseException("Failed to create client", e);
        }
    }

    private Client update(Client client) {
        String sql = "UPDATE clients SET name=?, cuit=?, address=?, phone=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCuit());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getPhone());
            stmt.setInt(5, client.getId());
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DatabaseException("Client not found with ID: " + client.getId());
            }
            LOGGER.info("Client updated with ID: " + client.getId());
            return client;
        } catch (SQLException e) {
            LOGGER.severe("Error updating client: " + e.getMessage());
            throw new DatabaseException("Failed to update client", e);
        }
    }

    @Override
    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToClient(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding client by ID: " + e.getMessage());
            throw new DatabaseException("Failed to find client", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding all clients: " + e.getMessage());
            throw new DatabaseException("Failed to retrieve clients", e);
        }
        return clients;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Client deleted with ID: " + id + " (rows affected: " + rowsAffected + ")");
        } catch (SQLException e) {
            LOGGER.severe("Error deleting client: " + e.getMessage());
            throw new DatabaseException("Failed to delete client", e);
        }
    }

    public List<Client> searchByName(String name) {
        List<Client> result = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE LOWER(name) LIKE ? ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToClient(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error searching clients by name: " + e.getMessage());
            throw new DatabaseException("Failed to search clients", e);
        }
        return result;
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        return new Client(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("cuit"),
            rs.getString("address"),
            rs.getString("phone")
        );
    }
}
