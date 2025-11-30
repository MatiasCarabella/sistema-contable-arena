package service;

import exception.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;
import model.Supplier;
import repository.SupplierRepository;

public class SupplierService {
  private static final Logger LOGGER = Logger.getLogger(SupplierService.class.getName());
  private final SupplierRepository supplierRepository;

  public SupplierService(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  public Supplier createSupplier(String name, String cuit, String address, String phone) {
    final Supplier supplier = new Supplier(0, name, cuit, address, phone);
    return supplierRepository.save(supplier);
  }

  public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
    final Supplier supplier =
        supplierRepository
            .findById(id)
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
    return supplierRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));
  }

  public List<Supplier> listSuppliers() {
    return supplierRepository.findAll();
  }

  public List<Supplier> searchSuppliers(String name) {
    return supplierRepository.searchByName(name);
  }
}
