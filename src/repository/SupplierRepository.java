package repository;

import config.DatabaseConfig;
import exception.DatabaseException;
import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class SupplierRepository implements Repository<Supplier, Integer> {
    private static final Logger LOGGER = Logger.getLogger(SupplierRepository.class.getName());

    @Override
    public Supplier save(Supplier supplier) {
        if (supplier.getId() == 0) {
            return create(supplier);
        } else {
            return update(supplier);
        }
    }

    private Supplier create(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, cuit, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getCuit());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getPhone());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    supplier.setId(rs.getInt(1));
                }
            }
            LOGGER.info("Supplier created with ID: " + supplier.getId());
            return supplier;
        } catch (SQLException e) {
            LOGGER.severe("Error creating supplier: " + e.getMessage());
            throw new DatabaseException("Failed to create supplier", e);
        }
    }

    private Supplier update(Supplier supplier) {
        String sql = "UPDATE suppliers SET name=?, cuit=?, address=?, phone=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getCuit());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getPhone());
            stmt.setInt(5, supplier.getId());
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DatabaseException("Supplier not found with ID: " + supplier.getId());
            }
            LOGGER.info("Supplier updated with ID: " + supplier.getId());
            return supplier;
        } catch (SQLException e) {
            LOGGER.severe("Error updating supplier: " + e.getMessage());
            throw new DatabaseException("Failed to update supplier", e);
        }
    }

    @Override
    public Optional<Supplier> findById(Integer id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToSupplier(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding supplier by ID: " + e.getMessage());
            throw new DatabaseException("Failed to find supplier", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error finding all suppliers: " + e.getMessage());
            throw new DatabaseException("Failed to retrieve suppliers", e);
        }
        return suppliers;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Supplier deleted with ID: " + id + " (rows affected: " + rowsAffected + ")");
        } catch (SQLException e) {
            LOGGER.severe("Error deleting supplier: " + e.getMessage());
            throw new DatabaseException("Failed to delete supplier", e);
        }
    }

    public List<Supplier> searchByName(String name) {
        List<Supplier> result = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE LOWER(name) LIKE ? ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToSupplier(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error searching suppliers by name: " + e.getMessage());
            throw new DatabaseException("Failed to search suppliers", e);
        }
        return result;
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("cuit"),
            rs.getString("address"),
            rs.getString("phone")
        );
    }
}
