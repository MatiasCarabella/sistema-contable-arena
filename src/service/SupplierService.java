package service;

import exception.EntityNotFoundException;
import exception.ValidationException;
import model.Supplier;
import repository.SupplierRepository;
import java.util.List;
import java.util.logging.Logger;

public class SupplierService {
    private static final Logger LOGGER = Logger.getLogger(SupplierService.class.getName());
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(String name, String cuit, String address, String phone) {
        validateSupplierData(name, cuit);
        Supplier supplier = new Supplier(0, name, cuit, address, phone);
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
        validateSupplierData(name, cuit);
        Supplier supplier = supplierRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));
        
        supplier.setName(name);
        supplier.setCuit(cuit);
        supplier.setAddress(address);
        supplier.setPhone(phone);
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(int id) {
        supplierRepository.deleteById(id);
        LOGGER.info("Supplier deleted with ID: " + id);
    }

    public Supplier findSupplier(int id) {
        return supplierRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));
    }

    public List<Supplier> listSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> searchSuppliers(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Search name cannot be empty");
        }
        return supplierRepository.searchByName(name);
    }

    private void validateSupplierData(String name, String cuit) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Supplier name cannot be empty");
        }
        if (cuit == null || cuit.trim().isEmpty()) {
            throw new ValidationException("Supplier CUIT cannot be empty");
        }
        if (!cuit.matches("\\d{2}-\\d{8}-\\d")) {
            throw new ValidationException("Invalid CUIT format. Expected: XX-XXXXXXXX-X");
        }
    }
}
