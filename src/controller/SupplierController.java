package controller;

import model.Supplier;
import repository.SupplierRepository;
import java.util.List;

public class SupplierController {
    private SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository repository) {
        this.supplierRepository = repository;
    }

    public Supplier createSupplier(String name, String cuit, String address, String phone) {
        Supplier supplier = new Supplier(0, name, cuit, address, phone);
        return supplierRepository.create(supplier);
    }

    public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
        Supplier supplier = supplierRepository.findById(id);
        if (supplier != null) {
            supplier.setName(name);
            supplier.setCuit(cuit);
            supplier.setAddress(address);
            supplier.setPhone(phone);
            supplierRepository.update(supplier);
        }
        return supplier;
    }

    public void deleteSupplier(int id) {
        supplierRepository.delete(id);
    }

    public Supplier findSupplier(int id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> listSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> searchSuppliers(String name) {
        return supplierRepository.searchByName(name);
    }
}
