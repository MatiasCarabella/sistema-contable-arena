package controller;

import model.Supplier;
import service.SupplierService;
import java.util.List;

/**
 * @deprecated Use {@link SupplierService} directly instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public Supplier createSupplier(String name, String cuit, String address, String phone) {
        return supplierService.createSupplier(name, cuit, address, phone);
    }

    public Supplier updateSupplier(int id, String name, String cuit, String address, String phone) {
        return supplierService.updateSupplier(id, name, cuit, address, phone);
    }

    public void deleteSupplier(int id) {
        supplierService.deleteSupplier(id);
    }

    public Supplier findSupplier(int id) {
        return supplierService.findSupplier(id);
    }

    public List<Supplier> listSuppliers() {
        return supplierService.listSuppliers();
    }

    public List<Supplier> searchSuppliers(String name) {
        return supplierService.searchSuppliers(name);
    }
}
