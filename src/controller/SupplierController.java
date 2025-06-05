package controller;

import model.Supplier;
import repository.SupplierRepository;
import java.util.List;

public class SupplierController {
    private SupplierRepository supplierRepo;

    public SupplierController(SupplierRepository repo) {
        this.supplierRepo = repo;
    }

    public Supplier registerSupplier(String name, String cuit, String address, String phone) {
        Supplier supplier = new Supplier(0, name, cuit, address, phone);
        return supplierRepo.save(supplier);
    }

    public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
        Supplier supplier = supplierRepo.findById(id);
        if (supplier != null) {
            supplier.setName(name);
            supplier.setCuit(cuit);
            supplier.setAddress(address);
            supplier.setPhone(phone);
            supplierRepo.save(supplier);
        }
        return supplier;
    }

    public void deleteSupplier(int id) {
        supplierRepo.delete(id);
    }

    public Supplier findSupplier(int id) {
        return supplierRepo.findById(id);
    }

    public List<Supplier> listSuppliers() {
        return supplierRepo.findAll();
    }

    public List<Supplier> searchSuppliers(String name) {
        return supplierRepo.searchByName(name);
    }
}
