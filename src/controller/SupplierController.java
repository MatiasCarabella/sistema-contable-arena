package controller;

import exception.ValidationException;
import java.util.List;
import model.Supplier;
import service.SupplierService;

/**
 * Controller for supplier operations. Handles input validation before delegating to service layer.
 */
public class SupplierController {
  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  public Supplier createSupplier(String name, String cuit, String address, String phone) {
    validateSupplierData(name, cuit);
    return supplierService.createSupplier(name, cuit, address, phone);
  }

  public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
    validateId(id);
    validateSupplierData(name, cuit);
    return supplierService.updateSupplier(id, name, cuit, address, phone);
  }

  public void deleteSupplier(int id) {
    validateId(id);
    supplierService.deleteSupplier(id);
  }

  public Supplier findSupplier(int id) {
    validateId(id);
    return supplierService.findSupplier(id);
  }

  public List<Supplier> listSuppliers() {
    return supplierService.listSuppliers();
  }

  public List<Supplier> searchSuppliers(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new ValidationException("Search name cannot be empty");
    }
    return supplierService.searchSuppliers(name);
  }

  private void validateId(int id) {
    if (id <= 0) {
      throw new ValidationException("ID must be positive");
    }
  }

  private void validateSupplierData(String name, String cuit) {
    validateName(name, "Supplier");
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
