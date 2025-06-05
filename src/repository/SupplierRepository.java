package repository;

import model.Supplier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierRepository {
    private Map<Integer, Supplier> suppliers = new HashMap<>();
    private int nextId = 1;

    public Supplier save(Supplier supplier) {
        if (supplier.getId() == 0) {
            supplier.setId(nextId++);
        }
        suppliers.put(supplier.getId(), supplier);
        return supplier;
    }

    public Supplier findById(int id) {
        return suppliers.get(id);
    }

    public List<Supplier> findAll() {
        return new ArrayList<>(suppliers.values());
    }

    public void delete(int id) {
        suppliers.remove(id);
    }

    public List<Supplier> searchByName(String name) {
        List<Supplier> result = new ArrayList<>();
        for (Supplier s : suppliers.values()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }
}
